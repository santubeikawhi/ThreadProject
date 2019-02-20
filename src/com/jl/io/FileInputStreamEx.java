package com.jl.io;

import java.io.*;

public class FileInputStreamEx {
    public static void main(String[] args){
        byte[] b = new byte[32];
        try {
            File f = new File("D:/test.png");
            InputStream fi = new FileInputStream(f);
            File f2 = new File("D:/hello.doc");
            OutputStream os = new FileOutputStream(f2);
            while(fi.read(b)!=-1){
                System.out.println(b);
                os.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
