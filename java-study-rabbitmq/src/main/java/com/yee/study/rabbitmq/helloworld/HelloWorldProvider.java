package com.yee.study.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的消息生产者
 *
 * @author Roger.Yi
 */
public class HelloWorldProvider {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldProvider.class);

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

        // 3. 发送消息
        String message = "Hello World, Guys!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        logger.info("Provider sent a message - [{}]", message);

        channel.close();
        connection.close();
    }
}
