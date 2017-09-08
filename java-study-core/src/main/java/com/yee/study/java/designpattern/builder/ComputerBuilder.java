package com.yee.study.java.designpattern.builder;

/**
 * 电脑组装类
 *
 * @author Roger.Yi
 */
public abstract class ComputerBuilder
{
    /**
     * 创建主板
     */
    abstract void buildMainboard();

    /**
     * 创建CPU
     */
    abstract void buildCpu();

    /**
     * 组装电脑
     */
    abstract Computer create();
}
