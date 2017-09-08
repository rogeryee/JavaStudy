package com.yee.study.java.designpattern.observer;

/**
 * 订阅者A
 * @author Roger.Yi
 */
public class SubscriberA implements Subscriber
{
    @Override
    public void onPublish()
    {
        System.out.println("SubscriberA received message.");
    }
}
