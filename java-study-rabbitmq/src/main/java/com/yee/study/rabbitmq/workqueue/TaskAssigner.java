package com.yee.study.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工作分配类（生产者）
 *
 * @author Roger.Yi
 */
public class TaskAssigner {

    private static final Logger logger = LoggerFactory.getLogger(TaskAssigner.class);

    private static final String TASK_QUEUE_NAME = "task";

    public static void main(String[] argv) throws Exception {

        // 1. 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // 2. 声明队列
        Channel channel = connection.createChannel();
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

        for (int i = 0; i < 10; i++) {
            String msg = "Hello World " + i;
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
            logger.info("Sent task - '" + msg + "'");

            Thread.sleep(2000);
        }

        channel.close();
        connection.close();
    }
}
