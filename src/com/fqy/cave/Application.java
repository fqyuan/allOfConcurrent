package com.fqy.cave;

class Demo1 extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Hello: " + i);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class Demo2 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Hello: " + i);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

public class Application {

	public static void main(String[] args) {
		Demo1 runner1 = new Demo1();
		runner1.start();

		Thread t2 = new Thread(new Demo2());
		t2.start();

	}
}