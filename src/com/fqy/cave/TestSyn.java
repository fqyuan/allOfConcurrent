package com.fqy.cave;

/*
 * synchronized means that in a multi threaded environment, an object having
 * synchronized method(s)/block(s) does not let two threads to access the
 * synchronized method(s)/block(s) of code at the same time. This means that
 * one thread can't read while another thread updates it.
 */
public class TestSyn {
	public static void main(String[] args) {
		TestSynUtil.demonstrate();
	}

}

class TestSynUtil {
	public static void demonstrate() {
		SharedObject sharedObject = new SharedObject();
		Thread t1 = new TestSynDemo(sharedObject, "Thread1");
		Thread t2 = new TestSynDemo(sharedObject, "Thread2");
		Thread t3 = new TestSynDemo(sharedObject, "Thread3");
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class SharedObject {
	// if not with synchronized keyword, result will be totally different
	public /* synchronized */void test(String name) {
		for (int i = 0; i < 5; i++) {
			System.out.println(name + ": " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class TestSynDemo extends Thread {
	private SharedObject sharedObject;
	private String name;

	public TestSynDemo(SharedObject sharedObject, String name) {
		this.sharedObject = sharedObject;
		this.name = name;
	}

	@Override
	public void run() {
		sharedObject.test(name);
	}
}