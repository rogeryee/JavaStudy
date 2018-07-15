package com.yee.study.rabbitmq.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志消息生产者（不同级别）
 *
 * @author Roger.Yi
 */
public class LogProvider {

    private static final Logger logger = LoggerFactory.getLogger(LogProvider.class);

    private static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 发送不同级别的日志
        String[] severities = {"error", "warning", "info"};
        for (String severity : severities) {
            String routingKey = severity;
            String message = "Hello World to " + routingKey;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            logger.info("Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }
}
