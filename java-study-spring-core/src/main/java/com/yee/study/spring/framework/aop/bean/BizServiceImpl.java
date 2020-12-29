package com.yee.study.spring.framework.aop.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务服务实现类
 *
 * @author Roger.Yi
 */
@Slf4j
public class BizServiceImpl implements BizInterface {

    @Override
    public String doBiz() {
        String msg = "do biz";
        log.info(msg);
        return msg;
    }

    @Override
    public String cancelBiz() {
        String msg = "cancel biz";
        log.info(msg);
        return msg;
    }
}
