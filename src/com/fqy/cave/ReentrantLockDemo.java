package com.fqy.cave;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

	public static void main(String[] args) {
		final Runner runner = new Runner();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
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
			e.printStackTrace();
		}
		runner.finished();
	}

}

class Runner {
	private int count = 0;
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();

	private void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}

	public void firstThread() throws InterruptedException {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " Waiting ...");
		cond.await();
		System.out.println(Thread.currentThread().getName() + " Woken up!");
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void secondThread() throws InterruptedException {
		Thread.sleep(1000);
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " Press the return key!");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		System.out.println(Thread.currentThread().getName() + " Got return key!");
		/*
		 * Wakes up one waiting thread.
		 * 
		 * If any threads are waiting on this condition then one is selected for
		 * waking up. That thread must then re-acquire the lock before returning
		 * from await. 这里重新获取锁什么意思？直接将🔒给了wait()的线程？
		 */
		cond.signal();
		// The following codes are executed, becasue it has not released the
		// lock.
		try {
			increment();
		} finally {
			lock.unlock();
		}
		// System.out.println("Running here?");
	}

	public void finished() {
		System.out.println("Count is: " + count);
	}
}