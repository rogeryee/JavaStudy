package com.yee.study.java.concurrent.pandc;

/**
 * 产品消费类
 *
 * @author Roger.Yi
 */
public class Consumer implements Runnable
{
    private String name;

    private Storage storage;

    public Consumer(String name, Storage storage)
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
                this.storage.consume(this.name);
                Thread.sleep(1000L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
