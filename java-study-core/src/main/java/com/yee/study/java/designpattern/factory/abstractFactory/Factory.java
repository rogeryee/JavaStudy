package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * 控件工厂接口
 *
 * @author RogerYee
 */
public interface Factory
{
    /**
     * 创建按钮
     * @return
     */
    Button createButton();

    /**
     * 创建标签
     * @return
     */
    Label createLabel();
}
