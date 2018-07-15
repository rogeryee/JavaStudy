package com.yee.study.rabbitmq.direct;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 错误日志消费者
 *
 * @author Roger.Yi
 */
public class ErrorConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ErrorConsumer.class);

    private static final String EXCHANGE_NAME = "direct_log";

    private static final String routingKey = "error";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

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
