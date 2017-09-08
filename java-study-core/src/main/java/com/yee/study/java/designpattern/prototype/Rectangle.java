package com.yee.study.java.designpattern.prototype;

/**
 * 长方形类
 *
 * @author Roger.Yi
 */
public class Rectangle extends Shape
{
    public Rectangle()
    {
        this.type = "Rectangle";
    }

    @Override
    void draw()
    {
        System.out.println("Draw a Rectangle.");
    }
}
