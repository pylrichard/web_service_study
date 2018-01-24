package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.bd.roncoo.eshop.datasync.service.service.EshopProductService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 见169-商品详情页动态渲染系统-消息队列架构升级之去重队列.md
 */
@Getter
public class QueueReceiver {
    @Autowired
    private EshopProductService eshopProductService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BRAND = "brand";
    private static final String CATEGORY = "category";
    private static final String PRODUCT_INTRO = "product_intro";
    private static final String PRODUCT_PROPERTY = "product_property";
    private static final String PRODUCT = "product";
    private static final String PRODUCT_SPECIFICATION = "product_specification";
    private static final long SEND_PERIOD = 100;
    /**
     * 数据同步服务不用每次立刻发送维度数据变更消息，可以将维度数据变更消息采用set方式，在内存中先进行去重
     */
    private Set<String> dimDataChangeMessageSet = Collections.synchronizedSet(new HashSet<String>());
    private String topic;

    public QueueReceiver(String topic) {
        this.topic = topic;
        new SendThread().start();
    }

    public void processMsg(String message) {
        //解析eshop_product_service中发送的消息为json格式数据
        JSONObject jsonObject = JSONObject.parseObject(message);
        //见eshop_product_service的createMsg()
        String dataType = jsonObject.getString("data_type");
        Long id = jsonObject.getLong("id");
        Long productId = jsonObject.getLong("product_id");
        if (BRAND.equals(dataType)) {
            processBrandDataChangeMessage(jsonObject, id);
        } else if (CATEGORY.equals(dataType)) {
            processCategoryDataChangeMessage(jsonObject, id);
        } else if (PRODUCT_INTRO.equals(dataType)) {
            processProductIntroDataChangeMessage(jsonObject, id, productId);
        } else if (PRODUCT_PROPERTY.equals(dataType)) {
            processProductPropertyDataChangeMessage(jsonObject, id, productId);
        } else if (PRODUCT.equals(dataType)) {
            processProductDataChangeMessage(jsonObject, id, productId);
        } else if (PRODUCT_SPECIFICATION.equals(dataType)) {
            processProductSpecificationDataChangeMessage(jsonObject, id, productId);
        }
    }

    public void processDataChangeMessage(JSONObject messageJSONObject, Long id, String data, String type) {
        String eventType = messageJSONObject.getString("event_type");
        String keyPrefix = type + "_";
        Jedis jedis = jedisPool.getResource();
        if ("add".equals(eventType) || "update".equals(eventType)) {
            JSONObject dataJSONObject = JSONObject.parseObject(data);
            //增加/修改消息则更新数据
            jedis.set(keyPrefix + id, dataJSONObject.toJSONString());
        } else if ("delete".equals(eventType)) {
            //删除消息则删除数据
            jedis.del(keyPrefix + id);
        }
        dimDataChangeMessageSet.add("{\"dim_type\": \"" + type + "\", \"id\": " + id + "}");
    }

    public void processBrandDataChangeMessage(JSONObject messageJSONObject, Long id) {
        processDataChangeMessage(messageJSONObject, id, eshopProductService.findBrandById(id), BRAND);
    }

    public void processCategoryDataChangeMessage(JSONObject messageJSONObject, Long id) {
        processDataChangeMessage(messageJSONObject, id, eshopProductService.findCategoryById(id), CATEGORY);
    }

    private void processProductIntroDataChangeMessage(JSONObject messageJSONObject, Long id, Long productId) {
        /*
            key中包含productId，供数据聚合服务进行聚合
            商品介绍可以进行分段存储，见177-商品详情页动态渲染系统-商品介绍分段存储以及分段加载的介绍
         */
        processDataChangeMessage(messageJSONObject, productId, eshopProductService.findProductIntroById(id), PRODUCT_INTRO);
    }

    private void processProductDataChangeMessage(JSONObject messageJSONObject, Long id, Long productId) {
        processDataChangeMessage(messageJSONObject, productId, eshopProductService.findProductById(id), PRODUCT);
    }

    private void processProductPropertyDataChangeMessage(JSONObject messageJSONObject, Long id, Long productId) {
        processDataChangeMessage(messageJSONObject, productId, eshopProductService.findProductPropertyById(id), PRODUCT_PROPERTY);
    }

    private void processProductSpecificationDataChangeMessage(JSONObject messageJSONObject, Long id, Long productId) {
        processDataChangeMessage(messageJSONObject, productId, eshopProductService.findProductSpecificationById(id), PRODUCT_SPECIFICATION);
    }

    /**
     * 将维度数据变化消息写入RabbitMQ中另外一个Queue，供数据聚合服务消费
     * <p>
     * 每隔一段时间将set的数据发送到下一个Queue中，然后将set的数据清除
     */
    private class SendThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (!dimDataChangeMessageSet.isEmpty()) {
                    for (String message : dimDataChangeMessageSet) {
                        rabbitMQSender.send(topic, message);
                    }
                    dimDataChangeMessageSet.clear();
                }
                try {
                    Thread.sleep(SEND_PERIOD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
