package com.fqy.cave;

public class TestDemo {
	/*
	 * synchronized means that in a multi threaded environment, an object having
	 * synchronized method(s)/block(s) does not let two threads to access the
	 * synchronized method(s)/block(s) of code at the same time. This means that
	 * one thread can't read while another thread updates it.
	 */
	public synchronized void test(String name) {
		for (int i = 0; i < 10; i++) {
			SOP.print(name + ":: " + i);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TestDemo demo = new TestDemo();
		new TestThread("Thread1", demo);
		new TestThread("Thread2", demo);
		new TestThread("Thread3", demo);
	}
}

class SOP {
	public static void print(String s) {
		System.out.print(s + "\n");
	}
}

class TestThread extends Thread {
	private String name;
	private TestDemo demo;

	public TestThread(String name, TestDemo demo) {
		this.name = name;
		this.demo = demo;
		start();
	}

	@Override
	public void run() {
		demo.test(name);
	}
}