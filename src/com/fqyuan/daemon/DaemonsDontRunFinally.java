package com.fqyuan.daemon;

/*
 * Daemons are terminated "abruptly" when the last of the non-daemons terminates.
 * As soon as main() exits, the JVM shuts down all the daemons immediately!
 */
import static com.fqyuan.utils.Print.print;

import java.util.concurrent.TimeUnit;

class ADaemon implements Runnable {
	public void run() {
		try {
			print("Starting ADaemon");
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			print("Exiting via InterruptedException");
		} finally {
			print("This should always run?");
		}
	}
}

public class DaemonsDontRunFinally {
	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new ADaemon());
		t.setDaemon(true);
		t.start();
		TimeUnit.MILLISECONDS.sleep(100);
	}
}