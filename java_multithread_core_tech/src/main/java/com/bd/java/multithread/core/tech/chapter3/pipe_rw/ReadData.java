package com.bd.java.multithread.core.tech.chapter3.pipe_rw;

import java.io.IOException;
import java.io.PipedReader;

public class ReadData {
    public void read(PipedReader reader) {
        try {
            System.out.println("read :");
            char[] buffer = new char[10];
            //读取线程先启动，此时没有数据写入，阻塞在此，有数据写入，继续向下运行
            int len = reader.read(buffer);
            while (len != -1) {
                String data = new String(buffer, 0, len);
                System.out.print(data);
                len = reader.read(buffer);
            }

            System.out.println();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
