package com.fqy.cave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PoolDemo {
	public static void main(String[] args) {
		/*
		 * What is the point of the thread pool? Creation and destruction of a
		 * thread carries with great overhead which we can avoid by using Thread
		 * pool.
		 */
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 5; i++) {
			// Submit a Runnable object for execution and return a future
			// representation that task.
			executor.submit(new PoolProcessor(i));
			// Execute the given code at some time in the future.
			// executor.execute(new PoolProcessor(i));
		}
		executor.shutdown();
		System.out.println("All tasks submited.");
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All tasks completed.");
	}
}

class PoolProcessor extends Thread {
	private int id;

	public PoolProcessor(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Starting " + id);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completing " + id);
	}
}