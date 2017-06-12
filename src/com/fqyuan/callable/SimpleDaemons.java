package com.fqyuan.callable;

import static com.fqyuan.utils.Print.print;

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable {
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				print(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			print("sleep() interrupted");
		}
	}

	/*
	 * There's nothing to keep the program from terminating once main() finishes
	 * its job since there are nothing but daemon threads running.
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Thread daemon = new Thread(new SimpleDaemons());
			/*
			 * Make the thread either a daemon or a user thread. JVM exits when
			 * the only threads running are all daemon threads. This methods
			 * must be invoked before the thread is started.
			 */
			daemon.setDaemon(true); // Must call before start()
			daemon.start();
		}
		print("All daemons started");
		/*
		 * You can see the results of starting all the daemon threads, the
		 * main() thread is briefly put to sleep. Without this, you can see only
		 * some of the results from the creation of the daemon threads.
		 */
		TimeUnit.MILLISECONDS.sleep(98); // Try various lengths to see behavior
	}

}
