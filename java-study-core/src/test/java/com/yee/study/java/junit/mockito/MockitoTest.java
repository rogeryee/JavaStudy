package com.yee.study.java.junit.mockito;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Roger.Yi
 */
public class MockitoTest
{
    @Test
    public void test()
    {
        // 1. Mock List类
        List<String> mockList = mock(List.class);
        when(mockList.get(0)).thenReturn("Hello");
        when(mockList.get(1)).thenReturn("World");

        assertEquals("Hello", mockList.get(0));
        assertEquals("World", mockList.get(1));

        verify(mockList, times(1)).get(0); // 验证指定方法被调用一次
        verify(mockList, never()).get(3); // 验证指定方法没有被调用
        verify(mockList, timeout(100).times(2)).get(anyInt()); // 验证get方法在100毫秒内被调用两次

        // 2. mock Iterator类
        Iterator iterator = mock(Iterator.class);
        when(iterator.next()).thenReturn("hello").thenReturn("world"); //预设当iterator调用next()时第一次返回hello，第n次都返回world

        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        assertEquals("hello world world", result);

        // 3. 匹配自定义参数
        List<Integer> list = mock(List.class);
        when(list.get(anyInt())).thenReturn(1);
//        assertEquals(1, list.get(1));
//        assertEquals(1, list.get(999));
        assertTrue(list.contains(1));
        assertTrue(!list.contains(3));
    }

    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException
    {
        OutputStream outputStream = mock(OutputStream.class);
        doThrow(new IOException()).when(outputStream).close(); // 预设当流关闭时抛出异常
        outputStream.close();
    }
}
