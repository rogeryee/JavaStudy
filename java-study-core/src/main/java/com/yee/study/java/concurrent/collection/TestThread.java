package com.yee.study.java.concurrent.collection;

import java.util.Collection;
import java.util.Iterator;

/**
 * 集合测试线程
 * <p>
 * 线程运行后会向集合中插入1000个随机数
 *
 * @author Roger.Yi
 */
public class TestThread extends Thread
{
    private Collection<Double> collection;

    private int capacity;

    private boolean printAfterAdd;

    public TestThread(Collection<Double> collection, int capacity, boolean printAfterAdd)
    {
        this.collection = collection;
        this.capacity = capacity;
        this.printAfterAdd = printAfterAdd;
    }

    public TestThread(Collection<Double> collection, int capacity)
    {
        this(collection, capacity, false);
    }

    @Override
    public void run()
    {
        for (int i = 0; i < capacity; ++i)
        {
            collection.add(Math.random());

            if(printAfterAdd)
            {
                printAll();
            }
        }
    }

    private void printAll()
    {
        Iterator iter = collection.iterator();
        while (iter.hasNext())
        {
            Double value = (Double) iter.next();
            System.out.print(value + ", ");
        }
    }
}
