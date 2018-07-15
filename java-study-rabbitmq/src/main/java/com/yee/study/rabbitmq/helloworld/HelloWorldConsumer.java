package com.yee.study.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 简单的消息消费者
 *
 * @author Roger.Yi
 */
public class HelloWorldConsumer {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldConsumer.class);

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        // 1. 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // 2. 声明消息队列
        // 声明参数如下：
        // 1）queue - 对列名
        // 2）durable - 队列是否持久
        // 3）exclusive - 是否排外的，一般等于true的话用于一个队列只能有一个消费者来消费的场景
        // 4）autoDelete - 是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除
        // 5）arguments - 队列参数
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        logger.info("Receiver is waiting for the message!");

        // 3. 消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info(" Receiver received message - ['" + message + "']");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
