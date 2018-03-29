package com.bd.java.multithread.core.tech.chapter1.suspend_resume;

public class LockStop extends Thread {
    private long i = 0;

    @Override
    public void run() {
        while(true) {
            i++;
            //LockStopTest.main()调用suspend()后
            //此处在PrintStream.println()内部停止，同步锁未释放
            //LockStopTest.main()的println("main end")不能进行打印
            System.out.println(i);
        }
    }
}
