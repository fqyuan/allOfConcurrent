package com.fqy.mix;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RetValThread {

	public static void main(String[] args) {
		RetValThreadUtil.demonstrate();
	}

}

class RetValThreadUtil {
	public static void demonstrate() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> future = executor.submit(new CallableTask());
		executor.shutdown();
		try {
			System.out.println("Sleeping time is: " + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			IOException ex = (IOException) (e.getCause());
			System.out.println(ex.getMessage());
		}
	}
}

class CallableTask implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Random random = new Random();
		int retVal = random.nextInt(4000);
		if (retVal > 2000)
			throw new IOException("Sleeping for too long!");
		System.out.println("Sleeping started.");
		Thread.sleep(retVal);
		System.out.println("Sleeping terminated.");
		return retVal;
	}
}