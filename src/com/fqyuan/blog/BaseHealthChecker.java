package com.fqyuan.blog;

import java.util.concurrent.CountDownLatch;

public abstract class BaseHealthChecker implements Runnable {
	private String name;
	private CountDownLatch latch;
	private boolean isServiceUp;

	public BaseHealthChecker(String name, CountDownLatch latch) {
		this.name = name;
		this.latch = latch;
		isServiceUp = false;
	}

	public String getName() {
		return name;
	}

	public boolean isServiceUp() {
		return isServiceUp;
	}

	@Override
	public void run() {
		try {
			verifyService();
			isServiceUp = true;
		} catch (Throwable t) {
			isServiceUp = false;
			t.printStackTrace(System.err);
		} finally {
			if (latch != null)
				latch.countDown();
		}
	}

	public abstract void verifyService();

}
