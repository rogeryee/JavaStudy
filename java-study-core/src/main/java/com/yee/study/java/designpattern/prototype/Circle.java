package com.yee.study.java.designpattern.prototype;

/**
 * 圆形类
 *
 * @author Roger.Yi
 */
public class Circle extends Shape
{
    public Circle()
    {
        this.type = "Circle";
    }

    @Override
    public void draw()
    {
        System.out.println("Draw a Circle.");
    }
}
