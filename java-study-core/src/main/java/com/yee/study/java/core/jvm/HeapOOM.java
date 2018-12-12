package com.yee.study.java.core.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出的示例
 *
 * @author Roger.Yi
 */
public class HeapOOM {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeapOOM.class);

    /**
     * 不断创建HeapOOM对象，最终导致堆区溢出
     * <p>
     * JVM Option ： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/RogerYee/Downloads/jvmdump
     *
     * @param args
     */
    public static void main(String[] args) {

        //使用list保持对对象的回收，防止Major gc（老年代回收）时回收对象。
        List<HeapOOM> list = new ArrayList<HeapOOM>();
        int count = 0;

        while (true) {
            try {
                HeapOOM heapOOM = new HeapOOM();
                list.add(heapOOM);
                count++;
            } catch (Exception ex) {
                LOGGER.error("Created " + count + " objects.");
            }

        }
    }
}
