package com.yee.study.spring.framework.aop.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务服务实现类(不实现任何接口，用于测试cglib代理)
 *
 * @author Roger.Yi
 */
public class AnotherBizService {

    private static final Logger log = LoggerFactory.getLogger(AnotherBizService.class);

    public String name = "default";

    public int id = 10;

    /**
     * 业务方法
     * @return
     */
    public String doMoreBiz() {
        String msg = "do more biz";
        log.info(msg);
        return msg;
    }
}
