package com.yee.study.java.core.jvm;

import com.yee.study.util.ArrayUtil;
import com.yee.study.util.CollectionUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author Roger.Yi
 */
public class JvmTest
{
    private static final int _1M = 1 * 1024 * 1024;

    private static final int _2M = 2 * 1024 * 1024;

    private static final int _3M = 3 * 1024 * 1024;

    private static final int _4M = 4 * 1024 * 1024;
    
    public static void main(String[] args) throws Exception
    {
        testAllocation1();
//        testAllocation2();
//
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * JVM : -verbose:gc -Xms50M -Xmx50M -Xmn20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
     */
    private static void testAllocation1()
    {
        Block allocation1, allocation2, allocation3, allocation4, allocation5, allocation6;
        allocation1 = new Block(_1M);
        allocation2 = new Block(_1M);
        allocation3 = new Block(_1M);
        allocation4 = new Block(_1M);
        allocation5 = new Block(_1M);
        allocation6 = new Block(_1M);
    }

    /**
     * JVM : -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     */
    private static void testAllocation2()
    {
        byte[] allocation1;
        allocation1 = new byte[_4M];
    }

    public static class Block {
        private byte[] allocation;

        public Block() {

        }

        public Block(int size) {
            this.setAllocation(new byte[size]);
        }

        public byte[] getAllocation() {
            return allocation;
        }

        public void setAllocation(byte[] allocation) {
            this.allocation = allocation;
        }
    }
}
