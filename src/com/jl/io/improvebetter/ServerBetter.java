package com.jl.io.improvebetter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBetter {

    private static int DEFAULT_PORT = 54321;

    private static ServerSocket server;

    //线程池 懒汉式的单例
    private static ExecutorService executorService = Executors.newFixedThreadPool(60);

    public static void start() throws IOException{
        start(DEFAULT_PORT);
    }

    public synchronized static void start(int port) throws IOException{
        if(server != null)return;
        try {
            server = new ServerSocket(port);
            System.out.println("服务端已启动，端口："+port);
            while(true){
                Socket socket = server.accept();
                if (executorService == null) {
                    synchronized (ExecutorService.class) {
                        executorService = Executors.newFixedThreadPool(60);
                    }
                }else{
                    //然后创建一个新的线程处理这条Socket链路
                    executorService.execute(new ServerHandler(socket));
                }
            }
        } finally {
            if(server != null){
                System.out.println("服务器已关闭。");
                server.close();
                server = null;
            }
        }
    }
}
