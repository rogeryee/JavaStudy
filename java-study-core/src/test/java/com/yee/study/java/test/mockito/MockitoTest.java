package com.yee.study.java.test.mockito;

import com.yee.study.util.CollectionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Roger.Yi
 */
public class MockitoTest {
    @Test
    @DisplayName("Verify Behaviors")
    public void testVerifyBehavior() {
        List mockedList = mock(List.class); // Mock Object

        mockedList.add("one"); // 操作1
        mockedList.clear(); // 操作2

        verify(mockedList).add("one"); // 验证操作1
        verify(mockedList).clear(); // 验证操作2
    }

    @Test
    @DisplayName("Stubbing")
    public void testVerifyStubbing() {
        // 1. Mock List
        List<String> mockList = mock(List.class); // Mock Object
        when(mockList.get(0)).thenReturn("Hello"); // stubbing
        when(mockList.get(1)).thenReturn("World"); // stubbing

        assertEquals("Hello", mockList.get(0)); // Assert
        assertEquals("World", mockList.get(1)); // Assert

        verify(mockList, times(1)).get(0); // 验证指定方法被调用一次
        verify(mockList, never()).get(3); // 验证指定方法没有被调用
        verify(mockList, timeout(100).times(2)).get(anyInt()); // 验证get方法在100毫秒内被调用两次

        // 2. Mock Iterator
        Iterator iterator = mock(Iterator.class);
        when(iterator.next()).thenReturn("hello").thenReturn("world"); //预设当iterator调用next()时第一次返回hello，第n次都返回world

        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        assertEquals("hello world world", result);
    }

    @Test
    @DisplayName("Argument Matchers")
    public void testArgumentMatchers() {
        //创建mock对象
        List<String> mock = mock(List.class);

        // 自定义了参数匹配，如果传入的参数为只包含2个元素的List，则表示匹配成功
        when(mock.addAll(argThat(new IsListContainsTwoElements()))).thenReturn(true);

        mock.addAll(Arrays.asList("three", "four", "five")); // List 包含3个元素，所以添加失败
        verify(mock).addAll(argThat(new IsListContainsTwoElements()));
    }

    private class IsListContainsTwoElements implements ArgumentMatcher<List> {
        @Override
        public boolean matches(List list) {
            return CollectionUtil.size(list) == 2;
        }
    }
}
