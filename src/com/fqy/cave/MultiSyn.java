package com.fqy.cave;

import java.util.ArrayList;
import java.util.Random;

public class MultiSyn {
	private Random random = new Random();

	private ArrayList<Integer> l1 = new ArrayList<>();
	private ArrayList<Integer> l2 = new ArrayList<>();
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			l1.add(random.nextInt(100));
		}
	}

	public void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			l2.add(random.nextInt(100));
		}
	}

	// synchronized
	public void process() {
		for (int i = 0; i < 100; i++) {
			stageOne();
			stageTwo();
		}
	}

	public static void main(String[] args) {
		MultiSyn m = new MultiSyn();
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				m.process();
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				m.process();
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
		long end = System.currentTimeMillis();
		System.out.println("Total time is " + (end - start));
		System.out.println(m.l1.size() + " " + m.l2.size());
	}
}
