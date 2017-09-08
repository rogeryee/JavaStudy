package com.yee.study.java.concurrent.aid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger 使用示例
 */
public class ExchangerSample
{
	public static void main(String[] args) throws InterruptedException
	{
		final Exchanger<String> exchanger = new Exchanger<String>();

		// Creates a Producer and a Thread to run it
		ProducerWithExchanger pe1 = new ProducerWithExchanger("Producer1", exchanger);
		ProducerWithExchanger pe2 = new ProducerWithExchanger("Producer2", exchanger);
		Thread p1 = new Thread(pe1, "Producer1");
		Thread p2 = new Thread(pe2, "Producer2");

		// Starts the thread
		p1.start();
		p2.start();
	}
}

class ProducerWithExchanger implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ProducerWithExchanger.class);

	private Exchanger<String> exchanger;

    private String name;

    private String data;

	public ProducerWithExchanger(String name, Exchanger<String> exchanger)
	{
        this.name = name;
		this.exchanger = exchanger;
	}

	public void run()
	{
		try
		{
			this.data = name + "-product";
            logger.info("Producer-" + name + ": before exchange data = " + data);
            this.data = this.exchanger.exchange(this.data);
            logger.info("Producer-" + name + ": after exchange data = " + data);
			TimeUnit.SECONDS.sleep(3);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
