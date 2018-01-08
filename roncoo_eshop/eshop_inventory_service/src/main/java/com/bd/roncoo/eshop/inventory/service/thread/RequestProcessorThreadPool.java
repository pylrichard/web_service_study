package com.bd.roncoo.eshop.inventory.service.thread;

import com.bd.roncoo.eshop.inventory.service.request.Request;
import com.bd.roncoo.eshop.inventory.service.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求处理线程池
 */
public class RequestProcessorThreadPool {
    /**
     * 线程池大小，每个线程监控的内存队列大小可以从配置文件读取
     */
    private static int THREAD_NUM = 10;
    private static int QUEUE_SIZE = 100;
    private ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUM);

    public RequestProcessorThreadPool() {
        /*
            一个队列对应一个工作线程
            每个工作线程串行执行队列中的请求
            比如工作线程完成写请求(数据库更新请求)后，执行下一个读请求(缓存更新请求，从数据库读取最新的值，然后刷新缓存)
         */
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < THREAD_NUM; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
            requestQueue.addQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }

    private static class Singleton {
        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }

    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    public static void init() {
        getInstance();
    }
}
