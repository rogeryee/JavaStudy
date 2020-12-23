package com.yee.study.java.core.lang;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * @author Roger.Yi
 */
@Slf4j
public class ReflectionSample {

    public static void main(String[] args) {
        log.info("Top.class.isInterface()=[{}]", Top.class.isInterface());
        log.info("SubTop.class.isInterface()=[{}]", SubTop.class.isInterface());
        log.info("Top.class.isAssignableFrom(SubTop.class)=[{}]", Top.class.isAssignableFrom(SubTop.class));

        log.info("SubTop getDeclaredMethods: ----------");
        Stream.of(SubTop.class.getDeclaredMethods()).forEach(method -> {
            log.info("SubTop method=[{}]", method);
        });

        log.info("SubTop2 getDeclaredMethods: ----------");
        Stream.of(SubTop2.class.getDeclaredMethods()).forEach(method -> {
            log.info("SubTop2 method=[{}]", method);
        });

        log.info("SubTop3 getDeclaredMethods: ----------");
        Stream.of(SubTop3.class.getDeclaredMethods()).forEach(method -> {
            log.info("SubTop3 method=[{}]", method);
        });

        log.info("SubTop3 getMethods: ----------");
        Stream.of(SubTop3.class.getMethods()).forEach(method -> {
            log.info("SubTop3 method=[{}]", method);
        });

        log.info("SubTop isVarArgs: ----------");
        Stream.of(SubTop.class.getDeclaredMethods()).forEach(method -> {
            log.info("SubTop method=[{}], isVarArgs=[{}], parameterCount=[{}]", method, method.isVarArgs(), method.getParameterCount());
        });
    }
}

interface Top {
}

class SubTop implements Top {

    public void test(String... names) {

    }

    public void test1(int id, String... names) {

    }

    public void test2(int id, String names) {

    }
}

class SubTop2 implements Top {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class SubTop3 extends SubTop2 {
    @Override
    public String toString() {
        return super.toString();
    }
}
