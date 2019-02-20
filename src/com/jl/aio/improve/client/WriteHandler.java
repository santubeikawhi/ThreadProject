package com.jl.aio.improve.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private CountDownLatch latch;
    private AsynchronousSocketChannel clientChannel;

    public WriteHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        if(buffer.hasRemaining()){
            clientChannel.write(buffer,buffer,this);
        }else{
            ByteBuffer readbuffer = ByteBuffer.allocate(1024);
            clientChannel.read(readbuffer,readbuffer,new ReadHandler(clientChannel,latch));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer buffer) {
        System.out.println("数据发送失败...");
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
