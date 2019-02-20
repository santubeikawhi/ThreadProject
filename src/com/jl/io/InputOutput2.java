package com.jl.io;

import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferCreator;

import java.io.*;
import java.util.stream.Stream;

public class InputOutput2 {
    public static void main(String[] args){
        try {
            FileOutputStream fos = new FileOutputStream("D:/output.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            osw.append("d测试");
            osw.append("\r\n");
            osw.append("test");
            osw.close();
            fos.close();
            FileInputStream fis = new FileInputStream("D:/output.txt");
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new Reader() {
                @Override
                public int read(char[] cbuf, int off, int len) throws IOException {
                    return 0;
                }

                @Override
                public void close() throws IOException {

                }
            });
            while(isr.ready()){
                sb.append((char)isr.read());
            }
            System.out.println(sb);
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
