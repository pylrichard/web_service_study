package com.bd.imooc.study.jms.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 主题模式下订阅者需要先启动，发布者后启动，否则订阅者无法接收到消息
 *
 * 先启动2个订阅者，再启动1个发布者，2个订阅者都会接收到发布者发送的全部消息
 */
public class AppSubscriber {
    private static final String url = "tcp://localhost:61616";
    //注意发布者和订阅者的主题名字要一致
    private static final String topicName = "topic-test";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);
            MessageConsumer consumer = session.createConsumer(destination);
            //异步监听消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("topic mode receive msg " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            //关闭连接则不能接收到消息
//            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
