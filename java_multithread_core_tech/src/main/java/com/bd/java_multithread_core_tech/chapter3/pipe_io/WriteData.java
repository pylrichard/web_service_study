package com.bd.java_multithread_core_tech.chapter3.pipe_io;

import java.io.IOException;
import java.io.PipedOutputStream;

public class WriteData {
    public void write(PipedOutputStream output) {
        try {
            System.out.println("write :");
            for (int i = 0; i < 50; i++) {
                String data = "" + (i + 1);
                output.write(data.getBytes());
                System.out.print(data + " ");
            }

            System.out.println();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
