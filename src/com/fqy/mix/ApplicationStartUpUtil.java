package com.fqy.mix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//The special design mode is needed. Including singleton pattern and util class to decouple
public class ApplicationStartUpUtil {
	private static List<BaseHealthChecker> list;
	private static CountDownLatch latch;

	private ApplicationStartUpUtil() {
	}

	private final static ApplicationStartUpUtil INSTANCE = new ApplicationStartUpUtil();

	public static ApplicationStartUpUtil getInstance() {
		return INSTANCE;
	}

	public static boolean checkExternalServices() throws InterruptedException {
		list = new ArrayList<>();
		latch = new CountDownLatch(3);

		// This is a small trick, just use special super constructor.
		list.add(new NetworkHealthChecker(latch));
		list.add(new CacheHealthChecker(latch));
		list.add(new DatabaseHealthChecker(latch));

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (BaseHealthChecker service : list)
			executor.submit(service);
		latch.await();
		for (BaseHealthChecker service : list) {
			if (service.isServiceUp() == false)
				return false;
		}
		return true;
	}
}
