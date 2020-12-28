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
    public void doBiz() {
        log.info("do biz ...");
    }

    @Override
    public void cancelBiz() {
        log.info("cancel biz ...");
    }
}
