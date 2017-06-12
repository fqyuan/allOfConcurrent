package com.fqyuan.share;

public class SynchronizedEvenGenerator extends IntGenerator {

	private int currentEventValue = 0;

	public synchronized int next() {
		++currentEventValue;
		Thread.yield();// Cause failure faster
		++currentEventValue;
		return currentEventValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}
}
