package com.fqyuan.atomicity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable {
	/*
	 * Since i is not volatile, there will be visibility problems. Both
	 * getValue() and evenIncrement() must be synchronized.
	 */
	private int i = 0;

	/*
	 * The program will find non-even values and terminate. Although is indeed
	 * an atomic operation, the lack of synchronization allows the value to be
	 * read while the object is in an unstable intermediate state.
	 */
	public int getValue() {
		return i;
	}

	private synchronized void evenIncrement() {
		i++;
		i++;
	}

	@Override
	public void run() {
		while (true) {
			evenIncrement();
		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicityTest at = new AtomicityTest();
		exec.execute(at);
		while (true) {
			int val = at.getValue();
			if (val % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}

}
