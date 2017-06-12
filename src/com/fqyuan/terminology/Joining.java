package com.fqyuan.terminology;

import static com.fqyuan.utils.Print.print;

/*
 * Understanding join().
 * One thread may call join() on another thread to wait for the second thread to complete 
 * before proceeding. If a thread calls t.join() on another thread t, then the calling thread is suspended until the target thread t 
 * finished(when t.isAlive() is false)
 */
class Sleeper extends Thread {
	private int duration;

	public Sleeper(String name, int sleepTime) {
		super(name);
		duration = sleepTime;
		start();
	}

	public void run() {
		try {
			// Causes the currently executing thread to sleep (temporarily cease
			// execution) for the specified number of milliseconds
			sleep(duration);
		} catch (InterruptedException e) {
			/*
			 * Tests whether this thread was interrupted. When another thread
			 * calls interrupt() on this thread, a flag is set to indicate that
			 * the thread has been interrupted. However, this flag is cleared
			 * when the exception is caught, so the result will always be false
			 * inside the catch clause.
			 */
			print(getName() + " was interrupted. " + "isInterrupted(): " + isInterrupted());
			return;
		}
		print(getName() + " has awakened");
	}
}

class Joiner extends Thread {
	private Sleeper sleeper;

	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}

	public void run() {
		try {
			/*
			 * Wait for this thread to die.
			 */
			sleeper.join();
		} catch (InterruptedException e) {
			print("Interrupted");
		}
		print(getName() + " join completed");
	}

}

public class Joining {
	/*
	 * 4个线程： sleepy, grumpy, dopey, doc被创建。 dopey: sleepy.join(); wait for sleep
	 * to finish doc: grumpy.join(); wait for grumpy to finish
	 * grumpy.interrupt(); //interrupt itself.
	 */

	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("Sleepy", 1500), grumpy = new Sleeper("Grumpy", 1500);
		Joiner dopey = new Joiner("Dopey", sleepy), doc = new Joiner("Doc", grumpy);
		/*
		 * Interrupt this thread. Unless current thread is interrupting itself,
		 * which is always permitted, the checkAccess() method of this thread is
		 * invoked, which may cause a SecurityException to be thrown.
		 */
		grumpy.interrupt();

	}
}
