package com.fqy.cave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphorDemo {

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 30; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					Connection.getInstance().doConnect();
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
}

class Connection {

	private static Connection instance = new Connection();
	private Semaphore semaphore = new Semaphore(10, true);
	private int connection = 0;

	private Connection() {

	}

	public static Connection getInstance() {
		return instance;
	}

	public void doConnect() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			connect();
		} finally {
			semaphore.release();
		}

	}

	public void connect() {
		synchronized (this) {
			connection++;
			System.out.println("Current connections: " + connection);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			connection--;
		}
	}
}