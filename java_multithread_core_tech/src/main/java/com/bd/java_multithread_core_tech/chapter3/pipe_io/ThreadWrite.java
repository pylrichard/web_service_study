package com.bd.java_multithread_core_tech.chapter3.pipe_io;

import java.io.PipedOutputStream;

public class ThreadWrite extends Thread {
    private WriteData write;
    private PipedOutputStream output;

    public ThreadWrite(WriteData write, PipedOutputStream output) {
        super();
        this.write = write;
        this.output = output;
    }

    @Override
    public void run() {
        write.write(output);
    }
}
