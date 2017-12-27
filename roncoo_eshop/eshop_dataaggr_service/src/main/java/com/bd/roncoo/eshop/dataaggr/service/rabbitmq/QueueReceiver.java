package com.bd.roncoo.eshop.dataaggr.service.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class QueueReceiver {
    @Autowired
    private JedisPool jedisPool;
    private static final String BRAND = "brand";
    private static final String CATEGORY = "category";
    private static final String PRODUCT_INTRO = "product_intro";
    private static final String PRODUCT = "product";

    public void processMsg(String message) {
        //解析eshop_datasync_service中发送的消息为json格式数据
        JSONObject messageJSONObject = JSONObject.parseObject(message);
        //见eshop_datasync_service的processDataChangeMessage()
        String dimType = messageJSONObject.getString("dim_type");
        if(BRAND.equals(dimType)) {
            processBrandDimDataChange(messageJSONObject);
        } else if(CATEGORY.equals(dimType)) {
            processCategoryDimDataChange(messageJSONObject);
        } else if(PRODUCT_INTRO.equals(dimType)) {
            processProductIntroDimDataChange(messageJSONObject);
        } else if(PRODUCT.equals(dimType)) {
            processProductDimDataChange(messageJSONObject);
        }
    }

    private void processDimDataChange(JSONObject messageJSONObject, String type) {
        Long id = messageJSONObject.getLong("id");
        Jedis jedis = jedisPool.getResource();
        /*
            品牌数据结构多变，有多个不同的表，不同的原子数据，将一个品牌对应的多个原子数据从Redis获取，聚合之后写入Redis
         */
        String dataJSON = jedis.get(type + "_" + id);
        if(dataJSON != null && !"".equals(dataJSON)) {
            jedis.set("dim_" + type + "_" + id, dataJSON);
        } else {
            jedis.del("dim_" + type + "_" + id);
        }
    }

    private void processBrandDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, BRAND);
    }

    private void processCategoryDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, CATEGORY);
    }

    private void processProductIntroDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, PRODUCT_INTRO);
    }

    private void processProductDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");
        Jedis jedis = jedisPool.getResource();
        /*
            通过mget批量查询数据，提高吞吐量
            List<String> results = jedis.mget("product_" + id, "product_property_" + id, "product_specification_" + id)
            String productDataJSON = results.get(0)
    		String productPropertyDataJSON = results.get(1)
    		String productSpecificationDataJSON = results.get(2)
         */
        String productDataJSON = jedis.get("product_" + id);
        if(productDataJSON != null && !"".equals(productDataJSON)) {
            JSONObject productDataJSONObject = JSONObject.parseObject(productDataJSON);
            String productPropertyDataJSON = jedis.get("product_property_" + id);
            if(productPropertyDataJSON != null && !"".equals(productPropertyDataJSON)) {
                productDataJSONObject.put("product_property", JSONObject.parse(productPropertyDataJSON));
            }
            String productSpecificationDataJSON = jedis.get("product_specification_" + id);
            if(productSpecificationDataJSON != null && !"".equals(productSpecificationDataJSON)) {
                productDataJSONObject.put("product_specification", JSONObject.parse(productSpecificationDataJSON));
            }
            jedis.set("dim_product_" + id, productDataJSONObject.toJSONString());
        } else {
            jedis.del("dim_product_" + id);
        }
    }
}
