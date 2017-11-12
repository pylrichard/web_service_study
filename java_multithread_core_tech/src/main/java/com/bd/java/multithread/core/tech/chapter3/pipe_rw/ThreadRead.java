package com.bd.java.multithread.core.tech.chapter3.pipe_rw;

import java.io.PipedReader;

public class ThreadRead extends Thread {
    private ReadData read;
    private PipedReader reader;

    public ThreadRead(ReadData read, PipedReader reader) {
        super();
        this.read = read;
        this.reader = reader;
    }

    @Override
    public void run() {
        read.read(reader);
    }
}
