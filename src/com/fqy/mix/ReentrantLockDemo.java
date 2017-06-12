package com.fqy.mix;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

	public static void main(String[] args) {
		ReentrantLockDemoUtil.demonstrate();
	}

}

class ReentrantLockDemoUtil {
	public static void demonstrate() {
		SharedObjectRe sharedObjectRe = new SharedObjectRe();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sharedObjectRe.stageOne();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				sharedObjectRe.stageTwo();
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sharedObjectRe.afterRunning();
	}
}

class SharedObjectRe {
	private int count = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	private void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}

	public void stageOne() throws InterruptedException {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " is waiting...");
		condition.await();
		System.out.println(Thread.currentThread().getName() + ", resumed.");
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void stageTwo() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + ", click to continue!");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		System.out.println(Thread.currentThread().getName() + ", enter key pressed.");
		condition.signal();
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void afterRunning() {
		System.out.println("The count is: " + count);
	}
}