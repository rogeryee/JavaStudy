package com.yee.study.designpattern.strategy;

/**
 * 高级会员策略类
 * @author Roger.Yi
 */
public class AdvancedMemberStrategy implements MemberStrategy
{
    private static final double DISCOUNT_RATE = 8.0;

    @Override
    public double calcPrice(double origin)
    {
        System.out.println("高级会员享受8折优惠.");
        return origin * DISCOUNT_RATE;
    }
}
