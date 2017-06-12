package com.fqyuan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

	public static void main(String[] args) {
		/*
		 * An Executor that provides methods to manage termination and methods
		 * that produces a Future for tracking progress of one or more
		 * asynchronous tasks.
		 */
		// Create a thread pool that creates new thread as needed
		// will reuse previously constructed threads as possible.
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			/*
			 * Execute the given command at some time in the future
			 */
			exec.execute(new LiftOff());
		/*
		 * Initialize an orderly shutdown in which previously submitted tasks
		 * are executed. no new tasks will be accepted.
		 */
		exec.shutdown();
	}

}
