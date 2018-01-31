package com.bd.roncoo.eshop.storm.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 日志解析Bolt
 */
public class LogParseBolt extends BaseRichBolt {
    private static final long serialVersionUID = -8017609899644290359L;
    private static final Logger logger = LoggerFactory.getLogger(LogParseBolt.class);
    private OutputCollector collector;

    @Override
    @SuppressWarnings("rawtypes")
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        //接收AccessLogKafkaSpout发送的日志
        String message = tuple.getStringByField("message");
        logger.info("LogParseBolt接收到一条日志 message=" + message);
        JSONObject messageJSON = JSONObject.parseObject(message);
        JSONObject uriArgsJSON = messageJSON.getJSONObject("uri_args");
        //获取商品id
        Long productId = uriArgsJSON.getLong("productId");
        if (productId != null) {
            //发送给ProductCountBolt.execute()
            collector.emit(new Values(productId));
            logger.info("LogParseBolt发送一个请求 productId=" + productId);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("productId"));
    }
}
