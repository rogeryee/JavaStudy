package com.yee.study.designpattern.strategy;

/**
 * 初始会员策略类
 * @author Roger.Yi
 */
public class PrimaryMemberStrategy implements MemberStrategy
{
    private static final double DISCOUNT_RATE = 1.0;

    @Override
    public double calcPrice(double origin)
    {
        System.out.println("初始会员没有折扣.");
        return origin * DISCOUNT_RATE;
    }
}
