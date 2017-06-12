package com.fqyuan.callable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {

	public static void main(String[] args) {
		/*
		 * An executor that provides method to manage termination and methods
		 * that method that produce a Future for tracking progress of one or
		 * more asynchronous tasks.
		 */
		ExecutorService exec = Executors.newCachedThreadPool();
		/*
		 * A Future represents the result of an asynchronous computation
		 */
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		for (int i = 0; i < 10; i++)
			/*
			 * Future<String>
			 * java.util.concurrent.ExecutorService.submit(Callable<String>
			 * task) submit a value-returning task for execution and return a
			 * Future representing the pending result of the task.
			 */
			results.add(exec.submit(new TaskWithResult(i)));
		for (Future<String> fs : results)
			try {
				// get() blocks until completion
				/*
				 * wait() if necessary for the computation to complete and then
				 * retrieve the result
				 */
				System.out.println(fs.get());
			} catch (InterruptedException e) {
				System.out.println(e);
				return;
			} catch (ExecutionException e) {
				System.out.println(e);
			} finally {
				exec.shutdown();
			}
	}

}
