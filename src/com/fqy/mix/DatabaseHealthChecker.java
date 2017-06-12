package com.fqy.mix;

import java.util.concurrent.CountDownLatch;

public class DatabaseHealthChecker extends BaseHealthChecker {

	public DatabaseHealthChecker(CountDownLatch latch) {
		super("Database service", latch);
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
