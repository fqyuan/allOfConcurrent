package com.fqy.git;

/*
 * Example: five threads download a same file, the task is called complete when 5
 * threads are all done.
 */
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * A synchronization aid that allows one or more threads to wait until a
		 * set of operations being performed in other threads completes.
		 */
		CountDownLatch latch = new CountDownLatch(2);
		Worker worker1 = new Worker("Worker A", latch);
		Worker worker2 = new Worker("Worker B", latch);
		worker1.start();
		worker2.start();
		// Cause current Thread to wait until the latch has counted down to
		// zero, unless the thread is interrupted.
		latch.await();
		System.out.println("All work done");
	}
}

class Worker extends Thread {
	String workerName;
	CountDownLatch latch;

	public Worker(String workerName, CountDownLatch latch) {
		this.workerName = workerName;
		this.latch = latch;
	}

	public void run() {
		System.out.println("Worker " + workerName + " start working.");
		try {
			Thread.sleep(5001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker " + workerName + " end working.");
		latch.countDown();// After working, counter minus 1
	}
}