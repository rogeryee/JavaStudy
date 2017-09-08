package com.yee.study.java.designpattern.strategy;

/**
 * 中级会员策略类
 * @author Roger.Yi
 */
public class IntermediateMemberStrategy implements MemberStrategy
{
    private static final double DISCOUNT_RATE = 9.0;

    @Override
    public double calcPrice(double origin)
    {
        System.out.println("中级会员享受9折优惠.");
        return origin * DISCOUNT_RATE;
    }
}
