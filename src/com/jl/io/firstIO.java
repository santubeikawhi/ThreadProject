package com.jl.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class firstIO {
        public static void main(String[] args){
            char c;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("输入字符, 按下 'q' 键退出。");
            try {
                do{
                    c = (char)br.read();
                    System.out.println(c);
                }while('q' !=c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
