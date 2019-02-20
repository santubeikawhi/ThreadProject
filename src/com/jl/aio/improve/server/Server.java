package com.jl.aio.improve.server;

public class Server {
    private static int DEFAULT_PORT = 54321;
    private static AsyncServerHandler serverHandler;
    public volatile static long clientcount = 0;

    public static void start(){
        start(DEFAULT_PORT);
    }

    public static void start(int port){
        if(serverHandler != null)
            return;
        serverHandler = new AsyncServerHandler(port);
        new Thread(serverHandler,"Server").start();
    }
}
