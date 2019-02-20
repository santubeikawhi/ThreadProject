package com.jl.nio.improve;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerHandle implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started;

    public ServerHandle(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            //true 通道为阻塞模式 false通道为非阻塞模式
            serverSocketChannel.configureBlocking(false);//设置为非阻塞模式
            //绑定通道端口，设置为1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //监听客户端连接请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //标记服务器已开启
            started = true;
            System.out.println("服务已启动，端口号："+port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        //循环遍历selector
        while(started){
            try {
                //无论是否有读写事件发生，selector每隔1秒被唤醒一次
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

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理新接入的请求消息
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                //通过ServerSocketChannel的accept创建SocketChannel实例
                //完成该操作意味着完成TCP三次握手，TCP物理链路正式连接
                SocketChannel sc = ssc.accept();
                //设置为非阻塞的
                sc.configureBlocking(false);
                //设置为读
                sc.register(selector,SelectionKey.OP_READ);
            }
            //读消息
            if(key.isReadable()){
                SocketChannel sc = (SocketChannel)key.channel();
                //创建ByteBuffer，并开辟一个1M的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流，并返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到字节，并对字节进行编解码
                if(readBytes > 0){
                    //将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
                    buffer.flip();
                    //根据缓冲区可读取的字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区读取的字节数组复制到新建的字节数组中
                    buffer.get(bytes);
                    String expression = new String(bytes,"UTF-8");
                    System.out.println("服务器接收到的信息："+expression);
                    String result = null;
                    try {
                        result = Calculator.cal(expression).toString();
                    } catch (ScriptException e) {
                        result = "计算出错"+ e.getMessage();
                    }
                    doWrite(sc,result);
                }else if(readBytes == 0){

                }else if(readBytes < 0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }
    //异步发送应答消息
    private void doWrite(SocketChannel channel,String response) throws IOException {
        //将消息编码为字节数组
        byte[] bytes = response.getBytes();
        //根据字节数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将发送的字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //将缓冲区的limit设置为position=0，用于后续的发送操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        channel.write(writeBuffer);
    }
}
