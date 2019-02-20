package com.jl.aio.improve.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel channel,AsyncServerHandler serverHandler) {
        Server.clientcount++;
        System.out.println("连接客户端数："+Server.clientcount);
        serverHandler.channel.accept(serverHandler,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer,buffer,new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
