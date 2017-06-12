package com.fqy.mix;

import java.util.concurrent.CountDownLatch;

public class CacheHealthChecker extends BaseHealthChecker {
	public CacheHealthChecker(CountDownLatch latch) {
		super("Cache Service", latch);
	}

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getServiceName());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.getServiceName() + " is Up");
	}
}
