package com.fqy.jenkov;

public class TryDef {
	public static void main(String[] args) {
		Thread t1 = new MyThread();
		t1.start();

		Thread t2 = new Thread() {
			@Override
			public void run() {
				System.out.println("Annoyed inner class");
			}
		};
		t2.start();

		Thread t3 = new Thread(new MyRunnable());
		t3.start();

		Runnable myRunnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Annoyed Implementing interface");
			}
		};
		Thread t4 = new Thread(myRunnable);
		t4.start();

		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 10; i++) {
			new Thread("" + i) {
				public void run() {
					System.out.println("Thread: " + getName() + " running");
				}
			}.start();
		}

	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println("Thread running");
	}
}

class MyRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println("Implementing interface!");
	}

}