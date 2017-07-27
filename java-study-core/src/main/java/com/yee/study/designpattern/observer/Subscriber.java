package com.yee.study.designpattern.observer;

/**
 * 订阅者接口
 * @author Roger.Yi
 */
public interface Subscriber
{
    /**
     * 收到消息
     */
    void onPublish();
}
