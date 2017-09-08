package com.yee.study.java.designpattern.observer;

/**
 * 消息发布类
 * @author Roger.Yi
 */
public class MessagePublisher extends AbstractPublisher
{
    @Override
    public void publish()
    {
        getSubscribers().forEach(Subscriber::onPublish);
    }
}
