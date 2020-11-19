package com.yee.study.spring.framework.utils.aop;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Roger.Yi
 */
@Slf4j
public class AdditionImpl implements IAddition {

    @Override
    public void doAdditional() {
        log.info("do addition");
    }
}
