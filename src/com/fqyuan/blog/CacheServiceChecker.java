package com.fqyuan.blog;

import java.util.concurrent.CountDownLatch;

public class CacheServiceChecker extends BaseHealthChecker {
	public CacheServiceChecker(CountDownLatch latch) {
		super("Cache Service", latch);
	}

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.getName() + " is up.");
	}
}
