package com.fqy.mix;

import java.util.concurrent.CountDownLatch;

public abstract class BaseHealthChecker implements Runnable {
	private CountDownLatch latch;
	private String serviceName;
	private boolean serviceUp;

	public BaseHealthChecker() {

	}

	public BaseHealthChecker(String serviceName, CountDownLatch latch) {
		this.latch = latch;
		this.serviceName = serviceName;
		this.serviceUp = false;
	}

	public String getServiceName() {
		return serviceName;
	}

	public boolean isServiceUp() {
		return serviceUp;
	}

	@Override
	public void run() {
		try {
			verifyService();
			serviceUp = true;
		} catch (Throwable t) {
			serviceUp = false;
			t.printStackTrace(System.err);
		} finally {
			if (latch != null)
				latch.countDown();
		}
	}

	public abstract void verifyService();
}
