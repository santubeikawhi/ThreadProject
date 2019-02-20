package com.jl.nio.improve;

public class Client {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 54321;
    public static ClientHandle clientHandle;
    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }

    public static synchronized  void start(String ip,int port){
        if(clientHandle != null){
            clientHandle.stop();
        }
        clientHandle = new ClientHandle(ip,port);
        new Thread(clientHandle,"Client").start();
    }

    public static boolean sendMsg(String msg) throws Exception {
        if(msg.equals("q")){
            return false;
        }else{
            clientHandle.sendMsg(msg);
            return true;
        }
    }
}
