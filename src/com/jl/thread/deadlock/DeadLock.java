package com.jl.thread.deadlock;

public class DeadLock {
    public static String str1 = "str1a";
    public static String str2 = "str2b";

    public static void main(String[] args){
        Thread a = new Thread(() -> {
            try{
                while(true){
                    System.out.println("T1开始访问 str1");
                    synchronized(DeadLock.str1){
                        System.out.println(Thread.currentThread().getName()+"锁住 "+DeadLock.str1);
                        Thread.sleep(3000);

                        System.out.println("T1开始访问 str2");
                        synchronized(DeadLock.str2){
                            System.out.println(Thread.currentThread().getName()+"锁住 "+DeadLock.str2);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        Thread b = new Thread(() -> {
            try{
                while(true){
                    System.out.println("T2开始访问 str2");
                    synchronized(DeadLock.str2){
                        System.out.println(Thread.currentThread().getName()+"锁住 "+DeadLock.str2);
                        Thread.sleep(3000);

                        System.out.println("T2开始访问 str1");
                        synchronized(DeadLock.str1){
                            System.out.println(Thread.currentThread().getName()+"锁住 "+DeadLock.str1);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        a.start();
        b.start();
    }
}
