package com.bd.java.multithread.core.tech.chapter3.pipe_rw;

import com.bd.java.multithread.core.tech.chapter3.pipe_rw.ReadData;
import com.bd.java.multithread.core.tech.chapter3.pipe_rw.ThreadRead;
import com.bd.java.multithread.core.tech.chapter3.pipe_rw.ThreadWrite;
import com.bd.java.multithread.core.tech.chapter3.pipe_rw.WriteData;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipeRWTest {
    public static void main(String[] args) {
        try {
            WriteData write = new WriteData();
            ReadData read = new ReadData();
            PipedReader reader = new PipedReader();
            PipedWriter writer = new PipedWriter();
            reader.connect(writer);

            ThreadRead rt = new ThreadRead(read, reader);
            rt.start();
            Thread.sleep(2000);
            ThreadWrite wt = new ThreadWrite(write, writer);
            wt.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
