package com.fqyuan.blog;

import java.util.Scanner;

public class WaitNotifyDemo {

	public static void main(String[] args) {
		WaitNotifyDemoUtil.demonstrate();
	}

}

class WaitNotifyDemoUtil {
	public static void demonstrate() {
		SharedObjectWait sharedObjectWait = new SharedObjectWait();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					sharedObjectWait.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					sharedObjectWait.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class SharedObjectWait {
	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Starting producing...");
			/*
			 * void java.lang.Object.wait() throws InterruptedException
			 * 
			 * Causes the current thread to wait until another thread invokes
			 * the java.lang.Object.notify() method or the
			 * java.lang.Object.notifyAll() method for this object. In other
			 * words, this method behaves exactly as if it simply performs the
			 * call wait(0).
			 * 
			 * The current thread must own this object's monitor. The thread
			 * releases ownership of this monitor and waits until another thread
			 * notifies threads waiting on this object's monitor to wake up
			 * either through a call to the notify method or the notifyAll
			 * method. The thread then waits until it can re-obtain ownership of
			 * the monitor and resumes execution.
			 * 
			 */
			this.wait();
			System.out.println("Consumed.");
		}
	}

	public void consume() throws InterruptedException {
		Thread.sleep(2000);
		Scanner scanner = new Scanner(System.in);
		synchronized (this) {
			System.out.println("Click enter key to continue.");
			scanner.nextLine();
			scanner.close();
			/*
			 * void java.lang.Object.notify()
			 * 
			 * Wakes up a single thread that is waiting on this object's
			 * monitor. If any threads are waiting on this object, one of them
			 * is chosen to be awakened. The choice is arbitrary and occurs at
			 * the discretion of the implementation. A thread waits on an
			 * object's monitor by calling one of the wait methods.
			 * 
			 * The awakened thread will not be able to proceed until the current
			 * thread relinquishes the lock on this object.
			 */
			this.notify();
		}
	}
}