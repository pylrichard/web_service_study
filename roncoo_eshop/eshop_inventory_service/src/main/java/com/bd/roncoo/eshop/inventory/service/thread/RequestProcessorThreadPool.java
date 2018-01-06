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
