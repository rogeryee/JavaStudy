package com.yee.study.java.designpattern.state;

/**
 * 电梯状态
 *
 * @author Roger.Yi
 */
public interface LiftState
{
    /**
     * 开门
     */
    void open();

    /**
     * 关门
     */
    void close();

    /**
     * 运行
     */
    void run();

    /**
     * 停止
     */
    void stop();
}
