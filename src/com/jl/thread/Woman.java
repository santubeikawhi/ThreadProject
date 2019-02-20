package com.jl.thread;

public class Woman implements Runnable {
    private Bank bank = new Bank();

    public void run() {
        int m = 100;
        int i=0;
        //bank.getMoney()>0
        while (i<5) {
            bank.drawMoney(m);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
