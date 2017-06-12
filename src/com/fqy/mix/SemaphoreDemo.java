package com.fqy.mix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
	public static void main(String[] args) {
		SemaphoreDemoUtil.demonstrate();
	}

}

class SemaphoreDemoUtil {
	public static void demonstrate() {
		Long startTime = System.currentTimeMillis();
		Connection connection = Connection.getInstance();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 30; i++) {
			executor.submit(new Runnable() {

				@Override
				public void run() {
					try {
						connection.doConnect();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("Total taken time is: " + (endTime - startTime));
	}
}

class Connection {
	private int count = 0;
	private Semaphore semaphore = new Semaphore(10);

	private Connection() {

	}

	private static final Connection INSTANCE = new Connection();

	public static Connection getInstance() {
		return INSTANCE;
	}

	private void connect() {
		synchronized (this) {
			count++;
			System.out.println("Current connections is: " + count);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			count--;
		}
	}

	public void doConnect() throws InterruptedException {
		try {
			semaphore.acquire();
			connect();
		} finally {
			semaphore.release();
		}

	}
}