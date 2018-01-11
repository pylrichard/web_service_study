package com.bd.roncoo.eshop.cache.server.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaConsumer implements Runnable {
    private ConsumerConnector consumerConnector;
    private String topic;

    public KafkaConsumer(String topic) {
        this.consumerConnector = Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        this.topic = topic;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<>(16);
        //用1个线程消费Topic
        topicCountMap.put(topic, 1);
        //key是topic
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                consumerConnector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        for (KafkaStream stream : streams) {
            new Thread(new KafkaMessageProcessor(stream)).start();
        }
    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "192.168.8.10:2181,192.168.8.11:2181,192.168.8.12:2181");
        props.put("group.id", "eshop_cache_group");
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }
}
