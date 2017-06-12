package com.fqy.cave;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> future = executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws IOException {
				Random random = new Random();
				int duration = random.nextInt(2000);
				if (duration > 1500)
					throw new IOException("Sleeping for two long!");
				System.out.println("Task starting...");
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Task ending...");
				return duration;
			}
		});
		executor.shutdown();
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			IOException ex = (IOException) (e.getCause());
			System.out.println(ex.getMessage());
			// System.out.println(e);
		}
	}

}
