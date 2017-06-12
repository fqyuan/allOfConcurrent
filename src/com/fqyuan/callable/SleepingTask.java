package com.fqyuan.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fqyuan.concurrency.LiftOff;

public class SleepingTask extends LiftOff {
	public void run() {
		try {
			while (countDown-- > 0) {
				System.out.print(status());
				// Old-style:
				/*
				 * Cause the current executing thread to sleep(temporarialy
				 * cease executing for the specified number of milliseconds.
				 */
				Thread.sleep(100);
				// Java SE5/6 style:
				/*
				 * Perform Thread.sleep() using this timeunit.
				 */
				TimeUnit.MILLISECONDS.sleep(100);
			}
			/*
			 * Because exceptions won't propagate across threads back to main()
			 * You must handle any exceptions that arise within a task?
			 */
		} catch (InterruptedException e) {
			System.err.println("Interrupted");
		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			exec.execute(new SleepingTask());
		exec.shutdown();
	}

}
