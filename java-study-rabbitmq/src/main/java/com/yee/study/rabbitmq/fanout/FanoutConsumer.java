package com.yee.study.rabbitmq.fanout;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 消息消费者（消费 fanout方式）
 * <p>
 * 可以启动多个实例
 *
 * @author Roger.Yi
 */
public class FanoutConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FanoutConsumer.class);

    private static final String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();
        String routingKey = "";
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        logger.info("Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info("Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
