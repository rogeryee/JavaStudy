package com.yee.study.spring.framework.aop.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Roger.Yi
 */
@Slf4j
public class Family {

    public static class Child {

        public void eat() {
            log.info("Child.eat food");
        }
    }

    public static class Mother {

        public void prepare() {
            log.info("Mother.prepare food");
        }

        public void clean() {
            log.info("Mother.clean table");
        }
    }
}
