package com.bd.roncoo.eshop.datasync.service.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Component  
@RabbitListener(queues = "data-change-queue")  
public class DataChangeQueueReceiver extends QueueReceiver {
	private List<JSONObject> brandDataChangeMessageList = new ArrayList<>();
	
	public DataChangeQueueReceiver() {
		super("aggr-data-change-queue");
	}
	
    @RabbitHandler  
    public void process(String message) {  
    	super.processMsg(message);
    }

	@Override
	public void processBrandDataChangeMessage(JSONObject messageJSONObject, Long id) {
		String eventType = messageJSONObject.getString("event_type");
		Jedis jedis = getJedisPool().getResource();
		if("add".equals(eventType) || "update".equals(eventType)) {
			brandDataChangeMessageList.add(messageJSONObject);
			if(brandDataChangeMessageList.size() >= 2) {
				String ids = "";
				for(int i = 0; i < brandDataChangeMessageList.size(); i++) {
					ids += brandDataChangeMessageList.get(i).getLong("id");
					if(i < brandDataChangeMessageList.size() - 1) {
						ids += ",";
					}
				}
				JSONArray brandJSONArray = JSONArray.parseArray(getEshopProductService().findBrandByIds(ids));
				for(int i = 0; i < brandJSONArray.size(); i++) {
					JSONObject dataJSONObject = brandJSONArray.getJSONObject(i);
					jedis.set("brand_" + id, dataJSONObject.toJSONString());
					getDimDataChangeMessageSet().add("{\"dim_type\": \"brand\", \"id\": " + id + "}");
				}
				brandDataChangeMessageList.clear();
			}
		} else if ("delete".equals(eventType)) {
			jedis.del("brand_" + id);
			getDimDataChangeMessageSet().add("{\"dim_type\": \"brand\", \"id\": " + id + "}");
		}
	}
}