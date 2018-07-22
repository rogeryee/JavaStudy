package com.yee.study.mysoa.loadbalance;

import com.yee.study.mysoa.consts.LoadBalanceRules;

import java.util.Map;

/**
 * @author Roger.Yi
 */
public class LoadBalanceFactory {

    private static Map<String, LoadBalanceRule> rules;

    static {
        rules.put(LoadBalanceRules.RANDOM, new RandomRule());
        rules.put(LoadBalanceRules.ROUND, new RoundRobinRule());
    }

    public static LoadBalanceRule getRule(String rule) {
        return rules.get(rule);
    }
}
