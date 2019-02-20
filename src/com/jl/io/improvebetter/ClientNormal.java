package com.jl.io.improvebetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNormal {
    private static int DEFAULT_SERVER_PORT = 54321;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";
    public static void send(String expression)throws IOException{
        send(expression,DEFAULT_SERVER_PORT);
    }
    public static void send(String expression,int port){
        System.out.println("算术表达式为："+expression);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket(DEFAULT_SERVER_IP,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(expression);
            System.out.println("____结果为："+in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
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
            }
            socket = null;
        }
    }
}
