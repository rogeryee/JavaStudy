package com.yee.study.designpattern.adapter.interfaceadapter;

/**
 * @author Roger.Yi
 */
public class DefaultTargetWrapper extends AbstractTargetWrapper
{
    @Override
    public void doOtherthing()
    {
        System.out.println("Wrapper doOtherthing.");
    }
}
