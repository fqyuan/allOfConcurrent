package com.fqyuan.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {

	public static void main(String[] args) {
		/*
		 * Use a single worker thread operating off an unbounded queue. Tasks
		 * are guaranteed to execute sequentially.
		 */
		ExecutorService exec = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		exec.shutdown();
	}

}
