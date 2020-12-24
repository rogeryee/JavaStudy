package com.yee.study.spring.framework.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.JdkIdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.time.Duration;
import java.time.Instant;

/**
 * Spring - IdGenerator 类示例
 * <p>
 * JdkIdGenerator: JDK的工具类包util包中就为我们提供了一个很好的工具类，即UUID。
 * AlternativeJdkIdGenerator 是Spring提供用来取代JDK的UUID的生成，它使用了SecureRandom作为种子，来替换调用UUID#randomUUID()
 * SimpleIdGenerator 类似于自增的Id生成器。每调用一次，自增1。一般很少使用
 *
 * @author Roger.Yi
 */
public class IdGeneratorTest {

    private static final Logger log = LoggerFactory.getLogger(IdGeneratorTest.class);

    @Test
    public void test() throws Exception {
        JdkIdGenerator jdkIdGenerator = new JdkIdGenerator();
        AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
        SimpleIdGenerator simpleIdGenerator = new SimpleIdGenerator();

        Instant start;
        Instant end;
        int count = 1000000;

        //jdkIdGenerator
        start = Instant.now();
        for (int i = 0; i < count; i++) {
            jdkIdGenerator.generateId();
        }
        end = Instant.now();
        log.info("JdkIdGenerator循环{}}次耗时：{} ms", count, Duration.between(start, end).toMillis());

        //alternativeJdkIdGenerator
        start = Instant.now();
        for (int i = 0; i < count; i++) {
            alternativeJdkIdGenerator.generateId();
        }
        end = Instant.now();
        log.info("AlternativeJdkIdGenerator循环{}}次耗时：{} ms", count, Duration.between(start, end).toMillis());

        //simpleIdGenerator
        start = Instant.now();
        for (int i = 0; i < count; i++) {
            simpleIdGenerator.generateId();
        }
        end = Instant.now();
        log.info("SimpleIdGenerator循环{}}次耗时：{} ms", count, Duration.between(start, end).toMillis());
    }
}
