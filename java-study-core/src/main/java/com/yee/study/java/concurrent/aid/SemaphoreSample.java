package com.yee.study.java.concurrent.aid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * Semaphore示例
 */
public class SemaphoreSample
{
	public static void main(String[] args)
	{
        // 生成一个同时只能有3个线程被执行的任务
		final TaskWithSemaphore task = new TaskWithSemaphore(3);

        // 同时启动10个线程
        for (int i = 0; i < 10; i++)
		{
            new Thread()
			{
				public void run()
				{
					task.startTask();
				}
			}.start();
		}
	}
}

/**
 * 任务类
 */
class TaskWithSemaphore
{
    private static final Logger logger = LoggerFactory.getLogger(TaskWithSemaphore.class);

    /**
     * 信号量
     */
	private final Semaphore semaphore;

	public TaskWithSemaphore(int permits)
	{
		this.semaphore = new Semaphore(permits);// 允许同时只能有 permits个线程可以执行
	}

    /**
     * 开始任务
     */
	public void startTask()
	{
		try
		{
			this.semaphore.acquire();

			logger.info(Thread.currentThread().getName()
					+ " acquire the semaphore and is doing the task! And current permits number is "
					+ this.semaphore.availablePermits());

			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.semaphore.release();
		}
	}
}
