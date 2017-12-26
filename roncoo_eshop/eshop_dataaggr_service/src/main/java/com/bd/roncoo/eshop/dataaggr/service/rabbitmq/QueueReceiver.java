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
        JSONObject messageJSONObject = JSONObject.parseObject(message);
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
        String dataJSON = jedis.get(type + "_" + id);
        if(dataJSON != null && !"".equals(dataJSON)) {
            jedis.set("dim_" + type + "_" + id, dataJSON);
        } else {
            jedis.del("dim_" + type + "_" + id);
        }
    }

    private void processBrandDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, "brand");
    }

    private void processCategoryDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, "category");
    }

    private void processProductIntroDimDataChange(JSONObject messageJSONObject) {
        processDimDataChange(messageJSONObject, "product_intro");
    }

    private void processProductDimDataChange(JSONObject messageJSONObject) {
        Long id = messageJSONObject.getLong("id");
        Jedis jedis = jedisPool.getResource();
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
