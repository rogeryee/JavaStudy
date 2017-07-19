package com.yee.study.designpattern.factory.simplefactory;

/**
 * 产品A
 *
 * @author RogerYee
 */
public class SampleA implements Sample
{
    @Override
    public void action()
    {
        // do something
        System.out.println("SampleA action.");
    }
}
