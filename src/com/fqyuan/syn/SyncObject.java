package com.fqyuan.syn;

import static com.fqyuan.utils.Print.print;

/*
 * DualSync.f() synchronizes on this(by synchronizing the entrie method)
 * and g() has a synchronized block that synchronizes on syncObject.
 * Thus, the two synchronizations are independent.
 * You can see from the output that both methods are running at the same time,
 * neither one is blocked by the synchronization of the other.
 */
class DualSynch {
	private Object syncObject = new Object();

	public synchronized void f() {
		for (int i = 0; i < 5; i++) {
			print("f()");
			Thread.yield();
		}
	}

	public void g() {
		synchronized (syncObject) {
			for (int i = 0; i < 5; i++) {
				print("g()");
				Thread.yield();
			}
		}
	}
}

public class SyncObject {
	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread() {
			public void run() {
				ds.f();
			}
		}.start();
		ds.g();
	}
}
