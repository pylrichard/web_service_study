package com.bd.java_multithread_core_tech.chapter1;

public class ThreadInterrupt4 extends Thread {
    @Override
    public void run() {
        super.run();

        try {
            for(int i = 0; i < 50000; i++) {
                if(this.isInterrupted()) {
                    System.out.println("cur " + this.currentThread().getName());
                    System.out.println("interrupted, exit");
                    throw new InterruptedException();
                }

                System.out.println("i = " + (i + 1));
            }

            System.out.println("chapter1 don't stop");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException catched");
            e.printStackTrace();
        }
    }
}
