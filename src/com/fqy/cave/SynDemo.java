package com.fqy.cave;

/*
 * synchronized keyword here is to deal with sharing resource issue.
 */
public class SynDemo {
	private int count = 0;

	// synchronized
	public void /* synchronized */ increment() {
		count++;
	}

	public void doWork() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1E4; i++)
					increment();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1E4; i++)
					increment();
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
		/*
		 * 如果没有synchronized同步该方法时，结果不是2000. Why？
		 * 1.线程对于共享对象变量的操作并不是原子的。3步，读取-加1-赋值。 2. 不知道何时从cache将值写入主存，所以出现了如下结果：
		 * main 1543（sometimes) 这就是所谓的：修改了部分值
		 */
		System.out.println(Thread.currentThread().getName() + " " + count);
	}

	public static void main(String[] args) {
		new SynDemo().doWork();
	}
}
