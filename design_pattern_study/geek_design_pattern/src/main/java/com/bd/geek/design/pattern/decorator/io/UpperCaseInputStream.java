package com.bd.geek.design.pattern.decorator.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpperCaseInputStream extends FilterInputStream {
    protected UpperCaseInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int character = super.read();

        return character == -1 ? character : Character.toUpperCase((char) (character));
    }

    @Override
    public int read(byte[] buffer, int offset, int len) throws IOException {
        int result = super.read(buffer, offset, len);
        for (int i = 0; i < result; i++) {
            buffer[i] = (byte) Character.toUpperCase((char) (buffer[i]));
        }

        return result;
    }
}
