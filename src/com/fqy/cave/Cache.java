package com.fqy.cave;

import java.util.Scanner;

class Processor extends Thread {
	// volatile keyword here is to ensure visibility
	private volatile boolean running = true;

	@Override
	public void run() {
		while (running) {
			System.out.println(Thread.currentThread().getName() + " Hello, Concurrent Programming!");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		running = false;
	}
}

/*
 * 多线程共享同一对象时，一个线程对线程所做的修改可能对于其他线程不可见！ 本例中有2个线程；主线程和用户线程p, 如果没有volatile关键字，
 * 主线程对于boolean变量running的操作可能对于用户线程不可见。
 */
public class Cache {
	public static void main(String[] args) {
		Processor p = new Processor();
		p.start();
		System.out.println("Press enter to stop executing...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		p.shutdown();
		System.out.println(Thread.currentThread().getName() + " terminated another thread!");

	}
}
