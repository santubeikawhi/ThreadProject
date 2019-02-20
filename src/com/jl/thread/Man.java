package com.jl.thread;

public class Man extends Thread {
    private Bank bank = new Bank();

    @Override
    public synchronized  void run() {
        int m = 100;
        int i=0;
        while (i<5) {
            System.out.println("名称："+this.getName());
            bank.saveMoney(m);
            /*try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
//            notifyAll();
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
