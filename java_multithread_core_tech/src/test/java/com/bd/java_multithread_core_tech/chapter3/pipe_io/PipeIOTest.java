package com.bd.java_multithread_core_tech.chapter3.pipe_io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeIOTest {
    public static void main(String[] args) {
        try {
            WriteData write = new WriteData();
            ReadData read = new ReadData();
            PipedInputStream input = new PipedInputStream();
            PipedOutputStream output = new PipedOutputStream();
            //2个Stream产生通信链接，数据可以进行输入和输出
            input.connect(output);

            ThreadRead rt = new ThreadRead(read, input);
            rt.start();
            Thread.sleep(2000);
            ThreadWrite wt = new ThreadWrite(write, output);
            wt.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
