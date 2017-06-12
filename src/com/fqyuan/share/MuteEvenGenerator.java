package com.fqyuan.share;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MuteEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	/*
	 * Lock implementations provide more extensive lock operations than can be
	 * obtained using synchronized methods and statements. A Lock tool is a tool
	 * for controlling access to a shared resource by multiple threads.
	 */
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		EvenChecker.test(new MuteEvenGenerator());
	}

	@Override
	public int next() {
		/*
		 * Acquire the lock. If the lock is not available, then the current
		 * thread becomes disabled for thread scheduling purposes and lies
		 * dormant until the lock has been acquired.
		 */
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield();// Cause failure faster
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

}
