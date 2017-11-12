package com.bd.java.multithread.core.tech.chapter3.pipe_rw;

import java.io.IOException;
import java.io.PipedWriter;

public class WriteData {
    public void write(PipedWriter writer) {
        try {
            System.out.println("write :");
            for (int i = 0; i < 50; i++) {
                String data = "" + (i + 1);
                writer.write(data);
                System.out.print(data + " ");
            }

            System.out.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
