package com.fqyuan.blog;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConcurrentQueue {

	public static void main(String[] args) {
		ConcurrentQueueUtil.demonstrate();
	}

}

class SharedObjectCon {
	BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
	Random random = new Random();

	public void produce() throws InterruptedException {
		while (true) {
			Thread.sleep(50);
			if (random.nextDouble() < 0.1)
				queue.put(random.nextInt(100));
		}
	}

	public void consume() throws InterruptedException {
		while (true) {
			Thread.sleep(100);
			if (random.nextInt(10) < 1) {
				int val = queue.take();
				System.out.println("Value taken is " + val + "; queue size is " + queue.size());
			}

		}
	}
}

class ConcurrentQueueUtil {
	public static void demonstrate() {
		SharedObjectCon sharedObjectCon = new SharedObjectCon();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sharedObjectCon.produce();
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
					sharedObjectCon.consume();
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
			e.printStackTrace();
		}

	}
}