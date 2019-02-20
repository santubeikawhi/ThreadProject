package com.jl.thread;

public class Bank {
    private static int money;
    public void saveMoney(int m){
        synchronized (this) {
            System.out.println("存钱后的总金额："+(money+=m));
        }
    }
    public void drawMoney(int m){
        synchronized (this) {
            Bank bank = new Bank();
            if (bank.getMoney()<=0) {
                System.out.println("没得钱，取个pi");
            }else {
                System.out.println("取钱后剩的总金额："+(money-=m));
            }
        }
    }
    public static int getMoney() {
        return money;
    }

    public static void setMoney(int money) {
        Bank.money = money;
    }

    public static void main(String[] args) {
        Man m1 = new Man();
        Man m2 = new Man();
        Man m3 = new Man();
        Woman w1 = new Woman();
        Woman w2 = new Woman();
        Woman w3 = new Woman();
        Thread t1 = new Thread(m1,"t1");
        Thread t2 = new Thread(m2,"t2");
        Thread t3 = new Thread(m3,"t3");
        Thread t4 = new Thread(w1);
        Thread t5 = new Thread(w2);
        Thread t6 = new Thread(w3);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
