package com.fqy.cave;

import java.util.LinkedList;
import java.util.Random;

public class ProducerDemo2 {

	public static void main(String[] args) {
		final Producer2Processor processor = new Producer2Processor();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.consume();
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

class Producer2Processor {
	private LinkedList<Integer> list = new LinkedList<>();
	private final int LIMIT = 10;
	private Object lock = new Object();
	private Random random = new Random();

	public void produce() throws InterruptedException {
		int value = 0;
		while (true) {
			synchronized (lock) {
				while (list.size() == LIMIT) {
					lock.wait();
				}
				list.add(value++);
				lock.notify();
			}
			Thread.sleep(random.nextInt(800));
		}
	}

	public void consume() throws InterruptedException {

		while (true) {
			synchronized (lock) {
				while (list.size() == 0) {
					lock.wait();
				}
				System.out.print("List size is: " + list.size());
				int value = list.removeFirst();
				System.out.println("; value is: " + value);
				// If the line is commented, the process will stop here.
				lock.notify();
			}
			Thread.sleep(random.nextInt(1000));
		}
	}
}
