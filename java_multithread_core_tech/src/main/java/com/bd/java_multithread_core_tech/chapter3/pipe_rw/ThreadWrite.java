package com.bd.java_multithread_core_tech.chapter3.pipe_rw;

import java.io.PipedWriter;

public class ThreadWrite extends Thread {
    private WriteData write;
    private PipedWriter writer;

    public ThreadWrite(WriteData write, PipedWriter writer) {
        super();
        this.write = write;
        this.writer = writer;
    }

    @Override
    public void run() {
        write.write(writer);
    }
}
