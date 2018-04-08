package com.bd.imooc.free.jms.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppPublisher {
    private static final String url = "tcp://localhost:61616";
    private static final String topicName = "topic-test";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //主题模式下目标与队列模式不同
            Destination destination = session.createTopic(topicName);
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
