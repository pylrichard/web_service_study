package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt1 extends Thread {
    @Override
    public void run() {
        super.run();

        /*
        try {
            File f = new File("./log.txt");
            f.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < 500000; i++) {
                out.write("i = " + i + "\r\n");
            }
            out.flush();
            out.close();

            PrintWriter writer = new PrintWriter("log.txt", "UTF-8");
            for (int i = 0; i < 500000; i++) {
                writer.println("i = " + i);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        for (int i = 0; i < 500; i++) {
            System.out.println("i = " + i);
        }
    }
}
