package com.bd.geek.design.pattern.decorator.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpperCaseInputStreamTest {
    public static void main(String[] args) {
        int character;
        try {
            InputStream inputStream = new UpperCaseInputStream(new BufferedInputStream(
                    new FileInputStream("D:\\input_stream_test.txt")));
            while ((character = inputStream.read()) >= 0) {
                System.out.print((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}