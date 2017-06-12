package com.fqyuan.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * List 并不是线程安全的，所以多个线程对同一个list对象写操作时就可能出现异常。
 * 1.这里可以直接synchronized process（）方法，但这样多线程执行效率低下。
 * 2.同样可以synchronized stageOne（）和stageTwo()，同样多线程只是按顺序完成，效率低下。
 * 3.所以这里应用的是代码块机制。
 */
public class SynOnMulti {

	public static void main(String[] args) {
		SynOnMultiUtil.demonstrate();
	}

}

class SharedObject {
	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private Random random = new Random();

	private /* synchronized */void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}

	private /* synchronized */void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}

	public /* synchronized */ void process() {
		for (int i = 0; i < 100; i++) {
			stageOne();
			stageTwo();
		}
	}
}

class SynOnMultiUtil {
	public static void demonstrate() {
		long startTime = System.currentTimeMillis();
		SharedObject sharedObject = new SharedObject();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				sharedObject.process();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				sharedObject.process();
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
		long endTime = System.currentTimeMillis();
		System.out.println("Total time taken is: " + (endTime - startTime));
	}
}