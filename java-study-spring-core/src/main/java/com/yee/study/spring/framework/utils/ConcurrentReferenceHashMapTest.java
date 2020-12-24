package com.yee.study.spring.framework.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.JdkIdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Spring - ConcurrentReferenceHashMap 类示例
 *
 * @author Roger.Yi
 */
public class ConcurrentReferenceHashMapTest {

    private static final Logger log = LoggerFactory.getLogger(ConcurrentReferenceHashMapTest.class);

    @Test
    public void test() throws Exception {
        String key = new String("key");
        String value = new String("val");
        Map<String, String> map = new ConcurrentReferenceHashMap<>(8, ConcurrentReferenceHashMap.ReferenceType.WEAK);
        map.put(key, value);
        log.info("map size is {}", map.size());
        log.info("map is {}", map);
        key = null;
        System.gc();

        //等待一会 确保GC能够过来
        TimeUnit.SECONDS.sleep(10);
        log.info("map size is {}", map.size());
        log.info("map is {}", map);
    }
}
