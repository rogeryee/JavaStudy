package com.yee.study.designpattern.adapter.classadapter;

/**
 * 类适配器
 *
 * @author Roger.Yi
 */
public class ClassAdapter extends Adaptee implements Target
{
    @Override
    public void doOtherthing()
    {
        System.out.println("Adapter doOtherthing.");
    }
}
