package com.jl.nio.improve;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean started;

    public ClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            socketChannel = SocketChannel.open();
            //true，通道为阻塞模式，false 通道为非阻塞模式
            socketChannel.configureBlocking(false);//设置为非阻塞模式
            //客户端启动标识
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //循环遍历selector
        while(started){
            try {
                //无论是否有读写事件发生，selector每隔1s被唤醒一次
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doConnect() throws IOException {
        //判断通道是否能正常连接服务端
        if(socketChannel.connect(new InetSocketAddress(host,port)));
        //通道设置为连接模式
        else socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理发送消息
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){

                }else{
                    System.exit(1);
                }
            }
            //读消息
            if(key.isReadable()){
                //创建ByteBuffer，并开辟一个1M的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流，并返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到的字节对字节进行编码
                if(readBytes>0){
                    //将缓冲区当前的limit设置为position=0，用于后续对缓冲区进行读取操作
                    buffer.flip();
                    //根据缓冲区可读取字节，创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读的字节数组复制到新创建的字节数组中
                    buffer.get(bytes);
                    String result = new String(bytes,"UTF-8");
                    System.out.println("客户端收到信息："+result);
                }else if(readBytes == 0){

                }else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel,String request) throws IOException {
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
    }

    public void sendMsg(String msg) throws Exception {
        socketChannel.register(selector,SelectionKey.OP_READ);
        doWrite(socketChannel,msg);
    }
}
