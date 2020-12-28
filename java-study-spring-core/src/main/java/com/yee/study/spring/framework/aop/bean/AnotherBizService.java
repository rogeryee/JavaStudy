package com.yee.study.spring.framework.aop.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务服务实现类(不实现任何接口，用于测试cglib代理)
 *
 * @author Roger.Yi
 */
@Slf4j
public class AnotherBizService {

    public String name = "default";
    public int id = 10;

    public void doMoreBiz() {
        log.info("do more biz ...");
    }
}
