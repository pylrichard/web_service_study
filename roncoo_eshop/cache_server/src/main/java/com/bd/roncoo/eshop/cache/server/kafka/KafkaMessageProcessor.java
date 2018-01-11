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

/**
 * 主动更新
 * 监听Kafka Topic，获取到商品数据变更消息之后，调用商品服务API获取数据，更新到EhCache和Redis中
 * 先获取分布式锁，然后比较更新时间，判断是否需要更新Redis
 */
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
            //提取消息对应的服务标识
            String serviceId = messageJSONObject.getString("serviceId");
            /*
                1 服务发送来数据变更消息(比如商品信息服务，商品店铺信息服务)，每个消息包含服务名以及商品id
                2 接收到消息后，根据商品id调用对应服务API获取数据，此处模拟获取到数据
                3 商品信息包含id、名称、价格、图片列表、商品规格、售后信息、颜色、尺寸
                4 商品店铺信息属于其他维度，用此维度模拟缓存数据维度化拆分，包含id、店铺名称、店铺等级、店铺好评率
                5 分别获取到数据后，将数据转换成json格式，分别存储到EhCache和Redis
             */
            if ("productInfoService".equals(serviceId)) {
                processProductInfoChangeMessage(messageJSONObject);
            } else if ("shopInfoService".equals(serviceId)) {
                processShopInfoChangeMessage(messageJSONObject);
            }
        }
    }

    /**
     * 处理商品信息变更消息
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
        //将数据写入Redis之前，先获取ZK分布式锁
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        zkSession.acquireDistributedLock(productId);
        //获取到锁后从Redis获取数据
        ProductInfo existedProductInfo = cacheService.getProductInfoFromRedisCache(productId);
        if (existedProductInfo != null) {
			/*
                比较当前数据的更新时间和已有数据的更新时间
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
        cacheService.saveProductInfo2RedisCache(productInfo);
        //释放分布式锁
        zkSession.releaseDistributedLock(productId);
    }

    /**
     * 处理店铺信息变更消息
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
        cacheService.saveShopInfo2RedisCache(shopInfo);
    }
}
