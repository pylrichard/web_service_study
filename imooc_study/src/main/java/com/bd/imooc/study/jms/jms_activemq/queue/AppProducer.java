package com.bd.imooc.study.jms.jms_activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppProducer {
//    private static final String url = "tcp://localhost:61616";
    /*
        高可用负载均衡集群按照mq.md进行配置后，Node A是消费者，url中不配置，见36.png
        启动生产者后，此时Node C为Master，浏览器输入http://192.168.8.10:8163，查看消息情况
        此时停止Node C，Node B成为Master，浏览器输入http://192.168.8.10:8162，查看消息情况
     */
    private static final String url = "failover:(tcp://localhost:61617,tcp://localhost:61618)?randomize=true";
    private static final String queueName = "queue-test";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 100; i++) {
                TextMessage textMessage = session.createTextMessage("test" + i);
                producer.send(textMessage);
                System.out.println("send msg " + textMessage.getText());
            }
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
