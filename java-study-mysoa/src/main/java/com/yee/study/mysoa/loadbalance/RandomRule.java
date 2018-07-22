package com.yee.study.mysoa.loadbalance;

import com.yee.study.mysoa.registry.RegistryService;
import com.yee.study.util.CollectionUtil;

import java.util.List;
import java.util.Random;

/**
 * 随机策略
 *
 * @author Roger.Yi
 */
public class RandomRule implements LoadBalanceRule {

    @Override
    public RegistryService select(List<RegistryService> serviceList) {
        Random random = new Random();
        int index = random.nextInt(CollectionUtil.size(serviceList));
        return CollectionUtil.getElement(serviceList, index);
    }
}
