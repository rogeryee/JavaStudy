package com.yee.study.designpattern.prototype;

/**
 * 正方形类
 *
 * @author Roger.Yi
 */
public class Square extends Shape
{
    public Square()
    {
        this.type = "Square";
    }

    @Override
    public void draw()
    {
        System.out.println("Draw a Square.");
    }
}
