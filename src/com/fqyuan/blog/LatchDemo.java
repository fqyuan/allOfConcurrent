package com.fqyuan.blog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchDemo {

	public static void main(String[] args) {
		LatchDemoUtil.demonstrate();
	}

}

class LatchDemoUtil {
	public static void demonstrate() {
		long startTime = System.currentTimeMillis();
		CountDownLatch latch = new CountDownLatch(5);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 5; i++) {
			executor.submit(new TaskWithLatch(latch));
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken is: " + (endTime - startTime));
	}
}

class TaskWithLatch implements Runnable {
	private CountDownLatch latch;

	public TaskWithLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	private void process() {
		System.out.println("Starting...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		latch.countDown();
		System.out.println("Ending...");
	}

	@Override
	public void run() {
		process();
	}
}