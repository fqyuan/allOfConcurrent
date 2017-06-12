package com.fqyuan.blog;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ListProducerConsumer {

	public static void main(String[] args) {
		ListProducerConsumerUtil.demonstrate();
	}

}

class ListProducerConsumerUtil {
	public static void demonstrate() {
		SharedObjectList sharedObjectList = new SharedObjectList();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					sharedObjectList.produce();
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
					sharedObjectList.consume();
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

class SharedObjectList {
	private List<Integer> list = new LinkedList<>();
	private final int LIMIT = 10;
	private Random random = new Random();
	private Object lock = new Object();
	private int cntValue = 0;

	public void consume() throws InterruptedException {
		while (true) {
			synchronized (lock) {
				if (list.size() == LIMIT)
					lock.wait();
				list.add(cntValue++);
				lock.notify();
			}
			Thread.sleep(random.nextInt(800));
		}
	}

	public void produce() throws InterruptedException {
		while (true) {
			synchronized (lock) {
				if (list.size() == 0)
					lock.wait();
				System.out.print("list size is " + list.size());
				int value = ((LinkedList<Integer>) list).removeFirst();
				System.out.println(", and removed value is: " + value);
				lock.notify();
			}
			Thread.sleep(random.nextInt(1000));
		}

	}
}