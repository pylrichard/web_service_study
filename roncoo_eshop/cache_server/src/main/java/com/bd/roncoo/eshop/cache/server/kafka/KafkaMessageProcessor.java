package com.bd.roncoo.eshop.cache.server.kafka;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.cache.server.model.ProductInfo;
import com.bd.roncoo.eshop.cache.server.model.ShopInfo;
import com.bd.roncoo.eshop.cache.server.service.CacheService;
import com.bd.roncoo.eshop.cache.server.spring.SpringContext;
import com.bd.roncoo.eshop.common.zk.ZooKeeperSession;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("rawtypes")
public class KafkaMessageProcessor implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private KafkaStream kafkaStream;
    private CacheService cacheService;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
        this.cacheService = (CacheService) SpringContext.getApplicationContext().getBean("cacheService");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());
            //将message转换成json对象
            JSONObject messageJSONObject = JSONObject.parseObject(message);
            //提取消息对应的服务的标识
            String serviceId = messageJSONObject.getString("serviceId");
            if ("productInfoService".equals(serviceId)) {
                processProductInfoChangeMessage(messageJSONObject);
            } else if ("shopInfoService".equals(serviceId)) {
                processShopInfoChangeMessage(messageJSONObject);
            }
        }
    }

    /**
     * 处理商品信息变更的消息
     */
    private void processProductInfoChangeMessage(JSONObject messageJSONObject) {
        //提取商品id
        Long productId = messageJSONObject.getLong("productId");
        /*
			TODO 调用商品服务API，比如getProductInfo?productId=5
		*/
        String productInfoJSON = "{\"id\": 5, \"name\": \"iPhone手机\", \"price\": 5599, " +
                "\"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iPhone的规格\", " +
                "\"service\": \"iPhone的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", " +
                "\"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:00:00\"}";
        ProductInfo productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);
        //将数据写入Redis之前，先获取zk分布式锁
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        zkSession.acquireDistributedLock(productId);
        //获取到锁后从Redis获取数据
        ProductInfo existedProductInfo = cacheService.getProductInfoFromReidsCache(productId);
        if (existedProductInfo != null) {
			/*
				比较当前数据的时间和已有数据的时间
			 */
            try {
                Date date = sdf.parse(productInfo.getModifiedTime());
                Date existedDate = sdf.parse(existedProductInfo.getModifiedTime());
                if (date.before(existedDate)) {
                    logger.info("current date[" + productInfo.getModifiedTime() + "] is before existed date["
                            + existedProductInfo.getModifiedTime() + "]");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("current date[" + productInfo.getModifiedTime() + "] is after existed date["
                    + existedProductInfo.getModifiedTime() + "]");
        } else {
            logger.info("existed product info is null......");
        }
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cacheService.saveProductInfo2LocalCache(productInfo);
        logger.info("获取保存到本地缓存的商品信息：" + cacheService.getProductInfoFromLocalCache(productId));
        cacheService.saveProductInfo2ReidsCache(productInfo);
        //释放分布式锁
        zkSession.releaseDistributedLock(productId);
    }

    /**
     * 处理店铺信息变更的消息
     */
    @SuppressWarnings("unused")
    private void processShopInfoChangeMessage(JSONObject messageJSONObject) {
        //提取商品id和店铺id
        Long productId = messageJSONObject.getLong("productId");
        Long shopId = messageJSONObject.getLong("shopId");
		/*
			TODO 调用店铺服务API，比如getShopInfo?shopId=1
		*/
        String shopInfoJSON = "{\"id\": 1, \"name\": \"小彭的手机店\", \"level\": 5, \"goodCommentRate\":0.99}";
        ShopInfo shopInfo = JSONObject.parseObject(shopInfoJSON, ShopInfo.class);
        cacheService.saveShopInfo2LocalCache(shopInfo);
        logger.info("获取保存到本地缓存的店铺信息：" + cacheService.getShopInfoFromLocalCache(shopId));
        cacheService.saveShopInfo2ReidsCache(shopInfo);
    }
}
