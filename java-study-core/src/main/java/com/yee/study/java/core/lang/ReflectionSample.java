package com.yee.study.java.core.lang;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
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

        log.info("SubTopAndBottom getInterfaces: ----------");
        Stream.of(SubTopAndBottom.class.getInterfaces()).forEach(inter -> {
            log.info("SubTopAndBottom interface=[{}]", inter.getName());
        });

        log.info("SubTopAndBottom getAllInterfaces: ----------");
        Set<Class<?>> clazzInterfaceSet = new LinkedHashSet<>();
        Class<?> current = SubTopAndBottom.class;
        while (current != null) {
            Class<?>[] ifcs = current.getInterfaces();
            clazzInterfaceSet.addAll(Arrays.asList(ifcs));
            current = current.getSuperclass();
        }
        clazzInterfaceSet.forEach(inter -> {
            log.info("SubTopAndBottom interface=[{}]", inter.getName());
        });
    }
}

interface Top {
}

interface Bottom {

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

class SubTopAndBottom extends SubTop3 implements Bottom {

}
