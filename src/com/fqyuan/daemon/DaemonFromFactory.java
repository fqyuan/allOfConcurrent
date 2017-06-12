package com.fqyuan.daemon;

import static com.fqyuan.utils.Print.print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DaemonFromFactory implements Runnable {
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				print(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			print("Interrupted");
		}
	}

	public static void main(String[] args) throws Exception {
		// ExecutorService method are overloaded to take a ThreadFactory object
		ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++)
			exec.execute(new DaemonFromFactory());
		print("All daemons started");
		TimeUnit.MILLISECONDS.sleep(150); // Run for a while
	}

}
