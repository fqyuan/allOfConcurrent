package com.fqyuan.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	public void run() {
		while (!generator.isCanceled()) {
			/*
			 * This program will eventually fail because the EvenChecker tasks
			 * are able to access the information in EvenGenerator while it's in
			 * an "incorrect" state. This is part of the problem with
			 * multi-threaded programs -- they can appear to be correct even
			 * when there's a bug, if the probability for failure is very low.
			 */
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				generator.cancel(); // Cancels all EvenCheckers
			}
		}
	}

	// Test any type of IntGenerator:
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++)
			/*
			 * java.util.concurrent.Executor.execute(Runnable command). execute
			 * given command in the future.
			 */
			exec.execute(new EvenChecker(gp, i));
		/*
		 * Initiates an orderly shutdown in which previously submitted tasks are
		 * executed, but no new tasks will be accepted. Invocation has no
		 * additional effect if already shut down.
		 */
		exec.shutdown();
	}

	// Default value for count:
	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}