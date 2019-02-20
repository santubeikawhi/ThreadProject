package com.jl.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BRWrite {
    public static void main(String[] args){
        String s = "dfasfdas";
        int b;
        b = 'A';
        System.out.write(b);
        System.out.write('\n');
        System.out.write(123123123);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            br.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
