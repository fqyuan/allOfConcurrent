package com.fqyuan.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LatchUtil {
	private static CountDownLatch latch;
	private static List<BaseHealthChecker> list;
	private static final LatchUtil INSTANCE = new LatchUtil();

	// Singleton pattern
	private LatchUtil() {

	}

	public static LatchUtil getInstance() {
		return INSTANCE;
	}

	public static boolean isServicesReady() {
		list = new ArrayList<>();
		latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);

		list.add(new NetServiceChecker(latch));
		list.add(new CacheServiceChecker(latch));
		list.add(new DatabaseServiceChecker(latch));
		for (BaseHealthChecker checker : list)
			executor.submit(checker);
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (BaseHealthChecker checker : list) {
			if (checker.isServiceUp() == false)
				return false;
		}
		return true;
	}
}
