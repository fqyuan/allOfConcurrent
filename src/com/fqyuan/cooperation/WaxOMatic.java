package com.fqyuan.cooperation;

import static com.fqyuan.utils.Print.print;
import static com.fqyuan.utils.Print.printnb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Car {
	private boolean waxOn = false;

	public synchronized void waxed() {
		waxOn = true; // Ready to buff
		/*
		 * Wake up all threads that are waiting on this thread's monitor. A
		 * thread waits on an object's monitor by calling one of the wait
		 * methods.
		 */
		notifyAll();
	}

	public synchronized void buffed() {
		waxOn = false; // Ready for another coat of wax
		notifyAll();
	}

	public synchronized void waitForWaxing() throws InterruptedException {
		while (waxOn == false)
			/*
			 * Cause the current thread to wait until another thread invoke
			 * notify() or notifyAll() for the object. The current thread must
			 * own this object's monitor. The thread releases ownership of this
			 * monitor and waits until another thread notifies threads waiting
			 * on this object's monitor to wake up either through a call to the
			 * notify method or the notifyAll method. The thread then waits
			 * until it can re-obtain ownership of the monitor and resumes
			 * execution.
			 */
			wait();
	}

	public synchronized void waitForBuffing() throws InterruptedException {
		while (waxOn == true)
			wait();
	}
}

class WaxOn implements Runnable {
	private Car car;

	public WaxOn(Car c) {
		car = c;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				printnb("Wax On! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			print("Exiting via interrupt");
		}
		print("Ending Wax On task");
	}
}

class WaxOff implements Runnable {
	private Car car;

	public WaxOff(Car c) {
		car = c;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				printnb("Wax Off! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			print("Exiting via interrupt");
		}
		print("Ending Wax Off task");
	}
}

/*
 * 3 threads are in the tasks in total. The main thread, the WaxOn thread, the
 * WaxOff thread.
 */
public class WaxOMatic {
	public static void main(String[] args) throws Exception {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOn(car));
		exec.execute(new WaxOff(car));
		TimeUnit.SECONDS.sleep(3); // Run for a while...
		exec.shutdownNow(); // Interrupt all tasks
	}
}