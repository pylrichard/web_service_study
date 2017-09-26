package com.bd.java_multithread_core_tech.chapter3.pipe_io;

import java.io.IOException;
import java.io.PipedInputStream;

public class ReadData {
    public void read(PipedInputStream input) {
        try {
            System.out.println("read :");
            byte[] buffer = new byte[10];
            //读取线程先启动，此时没有数据写入，阻塞在此，有数据写入，继续向下运行
            int len = input.read(buffer);
            while (len != -1) {
                String data = new String(buffer, 0, len);
                System.out.print(data);
                len = input.read(buffer);
            }

            System.out.println();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
