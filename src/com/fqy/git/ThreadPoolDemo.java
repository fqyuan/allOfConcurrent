package com.fqy.git;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadPoolDemo {

	public static void main(String[] args) throws InterruptedException {
		// ExecutorService exec = Executors.newFixedThreadPool(3);
		// ExecutorService exec = Executors.newCachedThreadPool();
		ExecutorService exec = Executors.newSingleThreadExecutor();

		final Semaphore semp = new Semaphore(5);
		Thread.sleep(3000);
		for (int index = 0; index < 50; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						semp.acquire();
						System.out.println("Accessing " + NO);
						Thread.sleep(1000);
						semp.release();
						System.out.println("----------------" + semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}

}
