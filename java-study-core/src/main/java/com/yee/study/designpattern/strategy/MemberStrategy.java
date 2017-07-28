package com.yee.study.designpattern.strategy;

/**
 * 会员策略接口
 * @author Roger.Yi
 */
public interface MemberStrategy
{
    /**
     * 计算折扣价
     * @param origin
     * @return
     */
    double calcPrice(double origin);
}
