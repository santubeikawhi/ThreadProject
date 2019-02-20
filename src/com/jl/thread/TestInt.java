package com.jl.thread;

public class TestInt {
        public static void main(String[] args){
            String[] strs = new String[]{"flower","flow","flight"};
            String result = "";
            String temp = "";
            for(int i=0;i<=strs.length-1;i++){
                if(i==0){
                    result = "";
                    temp=strs[i];
                }else{
                    String st = strs[i];
                    int si=0;
                    int length = (strs[i].length()>temp.length())?temp.length():strs[i].length();
                    System.out.println(length);
                    while(si<length-1){
                        String a = strs[i].substring(si,si+1);
                        String b = temp.substring(si,si+1);
                        System.out.println(a);
                        System.out.println(b);
                        if(a.equals(b)){
                            result += a;
                        }else{
                            break;
                        }
                        si++;
                    }
                }

            }
        }

}
