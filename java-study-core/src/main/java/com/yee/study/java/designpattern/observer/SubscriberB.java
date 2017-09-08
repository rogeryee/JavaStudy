package com.yee.study.java.designpattern.observer;

/**
 * 订阅者B
 * @author Roger.Yi
 */
public class SubscriberB implements Subscriber
{
    @Override
    public void onPublish()
    {
        System.out.println("SubscriberB received message.");
    }
}
