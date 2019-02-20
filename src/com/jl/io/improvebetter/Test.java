package com.jl.io.improvebetter;

import java.io.IOException;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerBetter.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100);

        char operators[] = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //随机产生算术表达式
                    String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);
                    try {
                        ClientNormal.send(expression);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.currentThread().sleep(random.nextInt(5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
