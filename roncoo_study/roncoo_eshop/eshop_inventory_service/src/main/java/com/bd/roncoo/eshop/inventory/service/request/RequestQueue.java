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
     * 内存队列，一个队列对应一个工作线程
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();
    /**
     * 标识位map，根据value为true还是false，判断之前执行的是写请求(数据库更新请求)还是读请求(缓存更新请求)
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
