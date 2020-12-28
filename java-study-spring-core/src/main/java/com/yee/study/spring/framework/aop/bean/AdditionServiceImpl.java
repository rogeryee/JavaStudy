package com.yee.study.spring.framework.aop.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * 附加服务实现类
 *
 * @author Roger.Yi
 */
@Slf4j
public class AdditionServiceImpl implements AdditionInterface {

    @Override
    public void doAdditional() {
        log.info("do addition");
    }
}
