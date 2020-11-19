package com.yee.study.spring.framework.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Roger.Yi
 */
@Slf4j
@Component
public class EmployeeComponent {

    public void doBiz() {
        log.info("do biz");
    }
}
