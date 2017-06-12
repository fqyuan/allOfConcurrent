package com.fqy.cave;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerDemo {
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					producer();
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
					consumer();
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

	private static void producer() throws InterruptedException {
		Random random = new Random();
		while (true) {
			// Inserts the specified element into this queue, waiting if
			// necessary for space to become available.
			Thread.sleep(50);
			if (random.nextDouble() < 0.1)
				queue.put(random.nextInt(100));
		}
	}

	private static void consumer() throws InterruptedException {
		Random random = new Random();
		while (true) {
			Thread.sleep(100);
			if (random.nextInt(10) == 0) {
				// Retrieves and removes the head of this queue, waiting if
				// necessary until an element becomes available.
				int val = queue.take();
				System.out.println("Taken value: " + val + "; Queue size is: " + queue.size());
			}
		}
	}
}
