package com.yee.study.java.concurrent.aid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 示例
 */
public class CyclicBarrierSample
{
	public static void main(String[] args)
	{
		int numberOfRunner = 5;
		CyclicBarrier cb = new CyclicBarrier(numberOfRunner, () -> System.out.println("All Runners are ready!"));

		for (int i = 0; i < numberOfRunner; i++)
		{
			new Thread(new RunnerWithCyclicBarrier(cb), "Player-" + (i + 1)).start();
		}
	}
}

/**
 * 跑步运动员类
 */
class RunnerWithCyclicBarrier extends Thread
{
    private static final Logger logger = LoggerFactory.getLogger(RunnerWithCyclicBarrier.class);

	private CyclicBarrier cb;

	public RunnerWithCyclicBarrier(CyclicBarrier cb)
	{
		this.cb = cb;
	}

	public void run()
	{
		try
		{
            logger.info(Thread.currentThread().getName() + " is ready!");
			cb.await();
            logger.info(Thread.currentThread().getName() + " starts running!");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}
}
