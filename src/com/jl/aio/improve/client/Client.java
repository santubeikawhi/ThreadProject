package com.jl.aio.improve.client;

public class Client {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 54321;
    private static AsyncClientHandler clientHandler;

    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }

    public static synchronized void start(String host,int port){
        if(clientHandler!=null)
            return;
        clientHandler = new AsyncClientHandler(host,port);
        new Thread(clientHandler,"Client").start();
    }

    public static boolean sendMsg(String msg){
        if(msg.equals('q')) return false;
        clientHandler.sendMsg(msg);
        return true;
    }
}
