package com.jl.nio.improve;

import java.util.Scanner;

public class StartServer {
    public static void main(String[] args) throws Exception {
        Server.start();
        /*Thread.sleep(1000);
        Client.start();
        while(true){
            Client.sendMsg(new Scanner(System.in).nextLine());
        }*/
    }
}
