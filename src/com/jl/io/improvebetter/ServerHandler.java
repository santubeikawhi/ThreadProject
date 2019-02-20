package com.jl.io.improvebetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable{
    public Socket getSocket() {
        return socket;
    }

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        String expression;
        String result;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            while(true){
                if((expression = in.readLine())==null) break;
                System.out.println("服务器收到消息："+expression);
                out.println("结果："+expression);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in  != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private Socket socket;
}
