package com.yee.study.java.core.concurrent.aid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 示例
 */
public class CountDownLatchSample
{
	public static void main(String[] args) throws InterruptedException
	{
        // 创建并启动一个有5人参加的会议
		int numberOfParticipant = 5;
		final ConferenceWithCountDownLatch conference = new ConferenceWithCountDownLatch(numberOfParticipant);
		conference.start();
        Thread.sleep(2000);

        // 参与者加入会议
		for (int i = 0; i < numberOfParticipant; i++)
		{
			new Thread("Participant-" + (i + 1))
			{
				public void run()
				{
					conference.enterConference(this.getName());
				}
			}.start();
		}
	}
}

/**
 * 会议类
 */
class ConferenceWithCountDownLatch extends Thread
{
    private static final Logger logger = LoggerFactory.getLogger(ConferenceWithCountDownLatch.class);

	private final CountDownLatch count;

	public ConferenceWithCountDownLatch(int numberOfParticipant)
	{
		this.count = new CountDownLatch(numberOfParticipant);
	}

	public void enterConference(String name)
	{
		logger.info(name + " enters the conference now!");
		this.count.countDown();
	}

	public void run()
	{
		try
		{
            logger.info("Conference is ready now and waiting for the participants!");
			this.count.await();
            logger.info("All the participant are ready, let's get started!");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
