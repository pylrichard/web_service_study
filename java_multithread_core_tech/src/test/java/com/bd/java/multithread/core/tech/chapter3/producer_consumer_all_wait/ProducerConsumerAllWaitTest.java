package com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait;

import com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait.Consumer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait.Producer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait.ThreadConsumer;
import com.bd.java.multithread.core.tech.chapter3.producer_consumer_all_wait.ThreadProducer;

public class ProducerConsumerAllWaitTest {
    public static int THREAD_NUM = 2;

    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");
        Producer producer = new Producer(lock);
        Consumer consumer = new Consumer(lock);
        ThreadProducer[] group1 = new ThreadProducer[THREAD_NUM];
        ThreadConsumer[] group2 = new ThreadConsumer[THREAD_NUM];

        for (int i = 0; i < THREAD_NUM; i++) {
            group1[i] = new ThreadProducer(producer);
            group1[i].setName("producer" + (i + 1));
            group2[i] = new ThreadConsumer(consumer);
            group2[i].setName("consumer" + (i + 1));
            //生产者线程先启动，执行notify()(属于通知过早)
            group1[i].start();
            group2[i].start();
        }

        Thread.sleep(3000);
        //获取当前线程所属线程组的活跃线程数(估计值)
        Thread[] group = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        //拷贝线程信息到group数组
        Thread.currentThread().getThreadGroup().enumerate(group);
        //所有的线程都是waiting状态
        for (int i = 0; i < group.length; i++) {
            System.out.println(group[i].getName() + " " + group[i].getState());
        }
    }
}
