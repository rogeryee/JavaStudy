package com.yee.study.rabbitmq.spring.sample;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 消息生产者（采用Spring-Rabbit）
 *
 * @author Roger.Yi
 */
public class MessageProducer {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:sample/producer.xml");
        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        String message = "This is a RabbitMq sample via Spring";
        template.send("fanout-ex", "", new Message(message.getBytes(), new MessageProperties()));

        Thread.sleep(Integer.MAX_VALUE);
    }
}
