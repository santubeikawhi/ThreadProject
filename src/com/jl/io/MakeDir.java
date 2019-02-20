package com.jl.io;

import java.io.File;

public class MakeDir {
    public static void main(String[] args){
        String dirname = "D:/test/test2";
        File d = new File(dirname);
        d.mkdirs();
    }
}
