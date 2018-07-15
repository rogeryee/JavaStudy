package com.yee.study.rabbitmq.spring.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 消息消费者（采用Spring-Rabbit）
 *
 * @author Roger.Yi
 */
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:sample/consumer.xml");

        Thread.sleep(Integer.MAX_VALUE);
    }
}

class H1Consumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(H1Consumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("H1 Consumer get message body=[{}]", message.toString());
    }
}

class H2Consumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(H2Consumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("H2 Consumer get message body=[{}]", message.toString());
    }
}

class H3Consumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(H3Consumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("H3 Consumer get message body=[{}]", message.toString());
    }
}
