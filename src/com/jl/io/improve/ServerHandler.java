package com.jl.io.improve;

import javax.script.ScriptException;
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
                //通过BufferedReader读取一行
                //如果已经读到输入流尾部，返回null,退出循环
                //如果得到非空值，就尝试计算结果并返回
                expression = in.readLine();
                System.out.println("获取时间戳"+System.currentTimeMillis());
                if(expression==null) {
                    break;
                }
                System.out.println("服务器收到消息："+expression);
                try {
                    result = Calculator.cal(expression).toString();
                } catch (ScriptException e) {
                    result = "计算错误"+e.getMessage();
                }
                out.println("结果："+result);
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
