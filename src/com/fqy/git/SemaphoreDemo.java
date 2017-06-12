package com.fqy.git;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
	public static void main(String[] args) {
		// This is so called thread pool!
		ExecutorService executorService = Executors.newCachedThreadPool();
		/*
		 * A counting semaphore. Conceptually, a semaphore maintains a set of
		 * permits. Each acquire blocks if necessary until a permit is
		 * available, and then takes it. Each release adds a permit, potentially
		 * releasing a blocking acquirer. However, no actual permit objects are
		 * used; the Semaphore just keeps a count of the number available and
		 * acts accordingly.
		 */
		final Semaphore semaphore = new Semaphore(5, true);
		// Only five threads are allowed
		for (int index = 0; index < 50; index++) {
			// Simulating 50 clients' access
			final int NO = index;
			Runnable run = new Runnable() {

				@Override
				public void run() {
					try {
						/*
						 * Acquires a permit from this semaphore, blocking until
						 * one is available, or the thread is interrupted.
						 * 
						 * Acquires a permit, if one is available and returns
						 * immediately, reducing the number of available permits
						 * by one.
						 */
						semaphore.acquire();
						System.out.println("Accessing " + NO);
						Thread.sleep((long) (Math.random() * 2000));
						semaphore.release();
						System.out.println("------------" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			// Executes the given command at some time in the future. The
			// command may execute in a new thread, in a pooled thread, or in
			// the calling thread, at the discretion of the Executor
			// implementation.
			executorService.execute(run);
		}
		// Exit the thread pool
		executorService.shutdown();
	}

}
