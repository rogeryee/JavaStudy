package com.yee.study.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象发布类
 * @author Roger.Yi
 */
public abstract class AbstractPublisher implements Publisher
{
    private static List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber sub)
    {
        subscribers.add(sub);
    }

    @Override
    public void unsubscribe(Subscriber sub)
    {
        subscribers.remove(sub);
    }

    public static List<Subscriber> getSubscribers()
    {
        return subscribers;
    }

    public static void setSubscribers(List<Subscriber> subscribers)
    {
        AbstractPublisher.subscribers = subscribers;
    }
}
