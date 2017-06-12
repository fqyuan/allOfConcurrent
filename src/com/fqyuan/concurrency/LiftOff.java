package com.fqyuan.concurrency;

/*
 * Demonstration of the Runnable interface
 * LiftOff, take off of a rocket...
 */
// implemented by any class whose instances are intended to be executed by a thread.
public class LiftOff implements Runnable {
	protected int countDown = 10; // Default

	/*
	 * The identifier id distinguishes between multiple instances of the task.
	 * It's final because it's not expected to change once it is initialized.
	 */
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {
	}

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + "), ";
	}

	/*
	 * 当一个对象实现了Runnable接口时，该类的instance可以用来创建一个thread start()
	 * 这个线程时，导致该实例的run()方法被调用！
	 */
	public void run() {
		while (countDown-- > 0) {
			System.out.print(status());
			/*
			 * A hint to the scheduler that the current thread is willing to
			 * yield its current use of the processor.
			 */
			Thread.yield();
		}
	}
}
