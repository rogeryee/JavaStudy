package com.yee.study.java.designpattern.strategy;

/**
 * 价格类
 * @author Roger.Yi
 */
public class Price
{
    private MemberStrategy strategy;

    public Price(MemberStrategy strategy)
    {
        this.strategy = strategy;
    }

    /**
     * 报价
     * @param originPrice
     * @return
     */
    public double quote(double originPrice)
    {
        return strategy.calcPrice(originPrice);
    }
}
