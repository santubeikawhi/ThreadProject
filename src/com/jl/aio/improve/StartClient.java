package com.jl.aio.improve;

import com.jl.aio.improve.client.Client;

import java.util.Scanner;

public class StartClient {
    public static void main(String[] args){
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while (Client.sendMsg(scanner.nextLine()));
    }
}
