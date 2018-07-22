package com.yee.study.mysoa.loadbalance;

import com.yee.study.mysoa.registry.RegistryService;
import com.yee.study.util.CollectionUtil;

import java.util.List;

/**
 * 随机策略
 *
 * @author Roger.Yi
 */
public class RoundRobinRule implements LoadBalanceRule {

    private static Integer index = 0;

    @Override
    public RegistryService select(List<RegistryService> serviceList) {

        int size = CollectionUtil.size(serviceList);
        synchronized (index) {
            if (index >= size) {
                index = 0;
            }

            return CollectionUtil.getElement(serviceList, index++);
        }
    }
}
