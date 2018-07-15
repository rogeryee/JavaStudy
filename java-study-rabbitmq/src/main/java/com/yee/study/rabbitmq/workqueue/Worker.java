package com.yee.study.rabbitmq.workqueue;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Work类（消息消费者）
 * 本例中可以启动多个，分别查看它们接受消息的情况
 *
 * @author Roger.Yi
 */
public class Worker {

    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private static final String TASK_QUEUE_NAME = "task";

    public static void main(String[] argv) throws Exception {

        // 1. 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();

        // 2. 声明队列
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        logger.info(" Waiting for messages...");

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                logger.info("Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    logger.info(" Task Done.");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    /**
     * 模拟工作
     *
     * @param task
     */
    private static void doWork(String task) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
