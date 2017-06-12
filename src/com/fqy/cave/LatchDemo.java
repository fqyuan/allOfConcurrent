package com.fqy.cave;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LatchProcessor implements Runnable {
	private CountDownLatch latch;

	public LatchProcessor(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Started.");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Decrease the CountDownLatch, release all waiting thread if the count
		// is zero.
		latch.countDown();
	}
}

public class LatchDemo {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			executor.submit(new LatchProcessor(latch));
		}
		// When latch is decreased to zero, release all waiting thread.
		try {
			// Cause current thread to wait until the latch has counted down to
			// zero.
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("After " + (end - start) + "ms, tasks Completed!");
	}
}
