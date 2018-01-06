package com.bd.roncoo.eshop.inventory.service.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求内存队列
 */
public class RequestQueue {
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();
    /**
     * 标识位map
     */
    private Map<Long, Boolean> flagMap = new ConcurrentHashMap<>();

    /**
     * 静态内部类初始化单例
     */
    private static class Singleton {
        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }

    }

    /**
     * 无论多少个线程并发执行初始化，内部类初始化只执行一次
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 添加一个内存队列
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 获取内存队列的数量
     */
    public int queueSize() {
        return queues.size();
    }

    /**
     * 获取内存队列
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    public Map<Long, Boolean> getFlagMap() {
        return flagMap;
    }
}
