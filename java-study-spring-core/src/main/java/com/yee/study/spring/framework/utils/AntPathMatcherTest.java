package com.yee.study.spring.framework.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.JdkIdGenerator;
import org.springframework.util.PathMatcher;
import org.springframework.util.SimpleIdGenerator;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Spring - AntPathMatcher 类示例
 *
 * @author Roger.Yi
 */
public class AntPathMatcherTest {

    private static final Logger log = LoggerFactory.getLogger(AntPathMatcherTest.class);

    /**
     * AntPathMatcher默认路径分隔符为“/”，而在匹配文件路径时，需要注意Windows下路径分隔符为“\”，Linux下为“/”。靠谱写法如下两种方式：
     * AntPathMatcher matcher = new AntPathMatcher(File.separator);
     * AntPathMatcher matcher = new AntPathMatcher(System.getProperty("file.separator"));
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        PathMatcher pathMatcher = new AntPathMatcher();

        // 精确匹配
        assertTrue(pathMatcher.match("/test", "/test"));
        assertFalse(pathMatcher.match("test", "/test"));

        //测试通配符?
        assertTrue(pathMatcher.match("t?st", "test"));
        assertTrue(pathMatcher.match("te??", "test"));
        assertFalse(pathMatcher.match("tes?", "tes"));
        assertFalse(pathMatcher.match("tes?", "testt"));

        //测试通配符*
        assertTrue(pathMatcher.match("*", "test"));
        assertTrue(pathMatcher.match("test*", "test"));
        assertTrue(pathMatcher.match("test/*", "test/Test"));
        assertTrue(pathMatcher.match("*.*", "test."));
        assertTrue(pathMatcher.match("*.*", "test.test.test"));
        assertFalse(pathMatcher.match("test*", "test/")); //注意这里是false 因为路径不能用*匹配
        assertFalse(pathMatcher.match("test*", "test/t")); //这同理
        assertFalse(pathMatcher.match("test*aaa", "testblaaab")); //这个是false 因为最后一个b无法匹配了 前面都是能匹配成功的

        //测试通配符** 匹配多级URL
        assertTrue(pathMatcher.match("/*/**", "/testing/testing"));
        assertTrue(pathMatcher.match("/**/*", "/testing/testing"));
        assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla/bla")); //这里也是true哦
        assertFalse(pathMatcher.match("/bla*bla/test", "/blaXXXbl/test"));

        assertFalse(pathMatcher.match("/????", "/bala/bla"));
        assertFalse(pathMatcher.match("/**/*bla", "/bla/bla/bla/bbb"));

        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing/"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/*", "/XXXblaXXXX/testing/testing/bla/testing"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing.jpg"));
        assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar"));

        //这个需要特别注意：{}里面的相当于Spring MVC里接受一个参数一样，所以任何东西都会匹配的
        assertTrue(pathMatcher.match("/{bla}.*", "/testing.html"));
        assertFalse(pathMatcher.match("/{bla}.htm", "/testing.html")); //这样就是false了
    }
}
