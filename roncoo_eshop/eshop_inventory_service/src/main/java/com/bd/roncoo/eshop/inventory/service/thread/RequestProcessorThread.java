package com.bd.roncoo.eshop.inventory.service.thread;

import com.bd.roncoo.eshop.inventory.service.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 */
public class RequestProcessorThread implements Callable<Boolean> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 被监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                //如果ArrayBlockingQueue是满的或者是空的，在执行request时会阻塞
                Request request = queue.take();
                logger.info("工作线程处理请求，商品id=" + request.getProductId());
                //执行request
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
