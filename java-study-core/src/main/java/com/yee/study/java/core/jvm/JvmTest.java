package com.yee.study.java.core.jvm;

/**
 * @author Roger.Yi
 */
public class JvmTest
{
    private static final int _1M = 1 * 1024 * 1024;

    private static final int _2M = 2 * 1024 * 1024;

    private static final int _3M = 3 * 1024 * 1024;

    private static final int _4M = 4 * 1024 * 1024;
    
    public static void main(String[] args)
    {
        testAllocation1();
//        testAllocation2();
    }

    /**
     * JVM : -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    private static void testAllocation1()
    {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5;
        allocation1 = new byte[_1M];
        allocation2 = new byte[_1M];
        allocation3 = new byte[_1M];
        allocation4 = new byte[_1M];
        allocation4 = new byte[_1M];
    }

    /**
     * JVM : -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     */
    private static void testAllocation2()
    {
        byte[] allocation1;
        allocation1 = new byte[_4M];
    }
}
