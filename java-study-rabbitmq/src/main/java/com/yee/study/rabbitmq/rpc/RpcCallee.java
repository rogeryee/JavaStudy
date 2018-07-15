package com.yee.study.rabbitmq.rpc;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * RPC响应者（消息消费者）
 *
 * @author Roger.Yi
 */
public class RpcCallee {

    private static final Logger logger = LoggerFactory.getLogger(RpcCallee.class);

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    /**
     * 业务逻辑（斐波那契求和）
     *
     * @param n
     * @return
     */
    private static int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) throws Exception {
        Connection connection = null;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();

            final Channel channel = connection.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);

            logger.info("Awaiting RPC requests....");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId())
                            .build();

                    String response = "";

                    try {
                        String message = new String(body, "UTF-8");
                        int n = Integer.parseInt(message);
                        logger.info(" Do fib(" + message + ")");
                        response += fib(n);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                        synchronized (this) {
                            this.notify();
                        }
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

            // 等待客户端调用
            while (true) {
                synchronized (consumer) {
                    consumer.wait();
                }
            }
        } finally {
            connection.close();
        }
    }
}
