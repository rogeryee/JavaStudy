package com.yee.study.java.designpattern.prototype;

/**
 * 原型模式客户端类
 *
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args) throws Exception
    {
        ShapeCache.init();

        Shape clonedShape = ShapeCache.getShape("1");
        clonedShape.draw();

        Shape clonedShape2 = ShapeCache.getShape("2");
        clonedShape2.draw();

        Shape clonedShape3 = ShapeCache.getDeepClonedShape("3");
        clonedShape3.draw();
    }
}
