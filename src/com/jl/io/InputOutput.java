package com.jl.io;

import java.io.*;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class InputOutput {
    public static void main(String[] args){
        int[] data = new int[]{1,12,13};
        try {
            OutputStream os = new FileOutputStream("D:/ipop.txt");
            int i = 0;
            while(i<=data.length-1){
                StringBuffer sb = new StringBuffer(data[i]);
                os.write(String.valueOf(data[i]).getBytes());
                i++;
            }
            InputStream fi = new FileInputStream("D:/ipop.txt");
            byte[] b= new byte[32];
            while(fi.read(b)!=-1){
                String s = new String(b);
                System.out.println(s);
            }
            fi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] int2ByteArray(int i){
        byte[] result=new byte[4];
        result[0]=(byte)((i >> 24)& 0xFF);
        result[1]=(byte)((i >> 16)& 0xFF);
        result[2]=(byte)((i >> 8)& 0xFF);
        result[3]=(byte)(i & 0xFF);
        return result;
    }
}
