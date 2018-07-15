package com.yee.study.rabbitmq.rpc;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * RPC请求发起者（消息生产者）
 *
 * @author Roger.Yi
 */
public class RpcCalller {

    private static final Logger logger = LoggerFactory.getLogger(RpcCalller.class);
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;

    public RpcCalller() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
    }

    public String call(String message) throws Exception {
        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void main(String[] argv)
    {
        RpcCalller fibonacciRpc = null;
        String response = null;
        try
        {
            fibonacciRpc = new RpcCalller();

            logger.info("Requesting fib(30)");
            response = fibonacciRpc.call("30");
            logger.info("Got '" + response + "'");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fibonacciRpc != null)
            {
                try
                {
                    fibonacciRpc.close();
                }
                catch (Exception ignore)
                {
                }
            }
        }
    }
}
