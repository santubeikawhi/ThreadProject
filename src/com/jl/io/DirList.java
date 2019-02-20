package com.jl.io;

import java.io.File;

public class DirList {
    public static void main(String[] args){
        String dirname = "D:/personal/workspace/ideaworkspace/ThreadProject";
        File file = new File(dirname);
        if(file.isDirectory()){
            System.out.println(" 目录"+dirname);
            String[] list = file.list();
            for(int i=0;i<= list.length-1;i++){
                File cfile = new File(dirname+"/"+list[i]);
                if(cfile.isDirectory()){
                    System.out.println(list[i] + " 是一个目录");
                }else{
                    System.out.println(list[i] + " 不是一个目录");
                }
            }
        }else{
            System.out.println(dirname + " 不是一个目录");
        }
    }
}
