package com.bd.imooc.free.jms.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 启动activemq
 * 1.activemq start
 * 2.浏览器输入http://192.168.8.10:8161，点击Manage ActiveMQ broker，用户名/密码为admin
 *
 * 先启动生产者2次，再启动消费者
 * 消费者会消费200条消息
 *
 * 同时启动2个消费者，1个生产者
 * 队列模式下2个消费者分别消费1个生产者发送的消息(1个消费奇数，1个消费偶数)
 */
public class AppConsumer {
//    private static final String url = "tcp://localhost:61616";
    private static final String url = "failover:(tcp://localhost:61616,tcp://localhost:61617,tcp://localhost:61618)?randomize=true";
    private static final String queueName = "queue-test";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(destination);
            //异步监听消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("queue mode receive msg " + textMessage.getText());
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
