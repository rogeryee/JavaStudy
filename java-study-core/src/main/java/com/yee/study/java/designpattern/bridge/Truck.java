package com.yee.study.java.designpattern.bridge;

/**
 * 卡车类
 * @author Roger.Yi
 */
public class Truck extends AbstractCar
{
    public void loadGoods()
    {
        System.out.println("Truck loadGoods.");
    }
}
