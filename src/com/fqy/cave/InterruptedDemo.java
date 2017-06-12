package com.fqy.cave;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//https://caveofprogramming.com/java-multithreading/java-multithreading-interrupting-threads-video-tutorial-part-14.html
public class InterruptedDemo {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting...");
		ExecutorService executor = Executors.newCachedThreadPool();

		// CPU intensive work that will use up the sleeping time of CPU
		Future<?> future = executor.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				Random random = new Random();
				for (int i = 0; i < 1E8; i++) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println(Thread.currentThread().getName() + " Interrupted! " + i);
						break;
					}
					Math.sin(random.nextDouble());
				}
				return null;
			}
		});
		// executor.shutdown();
		System.out.println("Thread main preparing to sleep 1000ms");
		Thread.sleep(1000);
		// The following code interrupt the running thread.
		executor.shutdownNow();

		executor.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Finished!");
	}
}
