package com.bd.roncoo.eshop.inventory.service.thread;

import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryCacheRefreshRequest;
import com.bd.roncoo.eshop.inventory.service.request.ProductInventoryDbUpdateRequest;
import com.bd.roncoo.eshop.inventory.service.request.Request;
import com.bd.roncoo.eshop.inventory.service.request.RequestQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 执行请求的工作线程
 */
public class RequestProcessorThread implements Callable<Boolean> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 对应的请求内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            //后台一直运行
            while (true) {
                //如果ArrayBlockingQueue是空的，则take()会阻塞；是满的，则put()会阻塞
                Request request = queue.take();
                logger.info("工作线程处理请求，商品id=" + request.getProductId());
                /*
                    Controller.findByProductId()中设置值
                 */
                boolean forceRefresh = request.isForceRefresh();
                /*
                    读请求(缓存更新请求)去重，减少内存队列中读请求积压，即减少每个请求执行等待时间
                 */
                if (!forceRefresh) {
                    RequestQueue requestQueue = RequestQueue.getInstance();
                    Map<Long, Boolean> flagMap = requestQueue.getFlagMap();
                    if (request instanceof ProductInventoryDbUpdateRequest) {
                        //如果是写请求(数据库更新请求)，将productId对应的标识设置为true
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        Boolean flag = flagMap.get(request.getProductId());
                        /*
                            表示写请求(数据库更新请求)/读请求(缓存更新请求)都没有处理过
                            包含2种情况:
                            1 数据库里没有相应的库存数据，可以直接返回
                            2 数据库里有初始库存数据，缓存中也有，但被Redis自动清理掉了，此时需要更新缓存
                            所以此处不能直接返回，需要更新缓存
                         */
                        if (flag == null) {
                            flagMap.put(request.getProductId(), false);
                        }
                        //如果是读请求(缓存刷新请求)，判断标识不为空而且是true，说明之前有1个写请求(数据库更新请求)
                        if (flag != null && flag) {
                            flagMap.put(request.getProductId(), false);
                        }
                        //判断标识不为空而且是false，说明之前有1个写请求(数据库更新请求)+1个读请求(缓存刷新请求)或者1个读请求(缓存刷新请求)
                        if (flag != null && !flag) {
                            //过滤多个读请求(缓存更新请求)，只需要执行1个即可
                            return true;
                        }
                    }
                }
                //执行request
                request.process();
                logger.info("工作线程处理请求，商品id=" + request.getProductId());
                /*
                    假如执行完一个读请求(缓存更新请求)之后，数据已经刷新到Redis
				    但是之后可能Redis因为内存满了自动清理掉相应数据
				    此时又发送来一个读请求(缓存更新请求)，发现标志位是false，就不会执行刷新操作了
				    执行完这个读请求之后，标志位还是false，导致缓存始终得不到更新，所以findByProductId()中读取数据库后需要更新缓存
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
