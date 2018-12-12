package com.yee.study.java.core.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 栈溢出示例
 * 
 * @author Roger.Yi
 */
public class StackOverflow {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackOverflow.class);

    private static int callLevel = 0;

    public static void func() {
        LOGGER.info("call func, level = {}", ++callLevel);
        func();
    }

    /**
     * 递归调用 func方法，导致栈溢出
     * <p>
     * JVM Option ： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/RogerYee/Downloads/jvmdump
     *
     * @param args
     */
    public static void main(String[] args) {
        func();
    }
}
