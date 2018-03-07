package com.yee.study.java.core.concurrent.pandc;

/**
 * 产品生产类
 *
 * @author Roger.Yi
 */
public class Producer implements Runnable
{
    private String name;

    private Storage storage;

    public Producer(String name, Storage storage)
    {
        this.name = name;
        this.storage = storage;
    }

    public void run()
    {
        for (;;)
        {
            try
            {
                this.storage.produce(this.name, new Product());
                Thread.sleep(1000L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
