package com.fqyuan.share;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		// Acquired the lock only if not held by another thread at the time of
		// invocation
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock(): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public void timed() {
		boolean captured = false;
		try {
			/*
			 * Acquire the lock if it is not held by another thread within the
			 * given time and the current thread has not been interrupted.
			 */
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public static void main(String[] args) {
		final AttemptLocking al = new AttemptLocking();
		al.untimed(); // True -- lock is available
		al.timed(); // True -- lock is available
		// Now create a separate task to grab the lock:
		new Thread() {
			{
				setDaemon(true);
			}

			public void run() {
				al.lock.lock();
				System.out.println("acquired");
				// al.lock.unlock();
			}
		}.start();
		Thread.yield(); // Give the 2nd task a chance
		al.untimed(); // False -- lock grabbed by task
		al.timed(); // False -- lock grabbed by task
	}
}