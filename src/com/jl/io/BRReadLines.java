package com.jl.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BRReadLines {
    public static void main(String[] args){
        String s = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            do{
                StringBuffer sb = new StringBuffer(br.readLine());
                s = sb.toString();
            }while(!("end".equals(s)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
