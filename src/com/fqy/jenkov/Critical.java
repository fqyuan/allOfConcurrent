package com.fqy.jenkov;

public class Critical {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Counter1().add(2);
				// System.out.println(new Counter().count);
			}
		});
		t1.start();

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Counter1().add(3);
				// System.out.println(new Counter().count);
			}
		});
		t2.start();

		System.out.println(new Counter1().count);
	}
}

class Counter1 {
	protected static int count = 0;

	public void add(int val) {
		count = val + count;
	}
}