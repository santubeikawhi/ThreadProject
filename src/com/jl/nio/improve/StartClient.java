package com.jl.nio.improve;

import java.util.Scanner;

public class StartClient {
    public static void main(String[] args) throws Exception {
        Client.start();
        Thread.sleep(1000);
        while(true){
            Client.sendMsg(new Scanner(System.in).nextLine());
        }
    }
}
