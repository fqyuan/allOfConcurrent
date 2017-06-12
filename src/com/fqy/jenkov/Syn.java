package com.fqy.jenkov;

public class Syn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Counter counter = new Counter();
		Thread threadA = new CounterThread(counter);
		Thread threadB = new CounterThread(counter);

		threadA.start();
		threadB.start();
	}

}

class Counter {
	long count = 0;

	public /* synchronized */void add(long value) {
		this.count += 1;

	}
}

class CounterThread extends Thread {

	protected Counter counter = null;

	public CounterThread(Counter counter) {
		this.counter = counter;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			counter.add(i);
			System.out.println(Thread.currentThread().getName() + " " + counter.count);
		}
	}
}