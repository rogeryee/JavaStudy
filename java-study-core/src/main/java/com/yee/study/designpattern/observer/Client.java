package com.yee.study.designpattern.observer;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Subscriber sub1 = new SubscriberA();
        Subscriber sub2 = new SubscriberA();

        Publisher publisher = new MessagePublisher();
        publisher.subscribe(sub1);
        publisher.subscribe(sub2);
        publisher.publish();
    }
}
