package com.fqyuan.concurrency;

/* The most basic use of the Thread class.
 * The traditional way to turn a Runnable object into a working task
 * Hand the Runnable object to a Thread constructor. 
 */
public class BasicThreads {

	public static void main(String[] args) {
		Thread t1 = new Thread(new LiftOff());
		/*
		 * Cause the current thread to begin execution The JVM calls the run
		 * method of this thread.
		 */
		t1.start();
		/*
		 * The following output comes at first. You can see from the output that
		 * the start() quickly returns. In effect, you call LiftOff.run(), and
		 * the method has not yet finished, because LiftOff.run() is being
		 * executed by a different thread, so you can perform another operations
		 * in the main() thread. Thus, the program is running two methods at
		 * once: main() and LiftOff.run().
		 */
		System.out.println("Waiting for LiftOff");
	}
	/*
	 * The running result: Waiting for LiftOff #0(9), #0(8), #0(7), #0(6),
	 * #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!),
	 */
}
