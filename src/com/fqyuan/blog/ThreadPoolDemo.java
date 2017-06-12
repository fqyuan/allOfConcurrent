package com.fqyuan.blog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
	public static void main(String[] args) {
		ThreadPoolDemoUtil.demonstrate();
	}
}

class ThreadPoolDemoUtil {
	public static void demonstrate() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 6; i++) {
			executor.submit(new Task(i));
		}
		System.out.println("All tasks submited.");
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All tasks finished.");
	}
}

class Task implements Runnable {
	private int id;

	public Task(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println(id + " Starting.");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id + " Ending.");
	}
}