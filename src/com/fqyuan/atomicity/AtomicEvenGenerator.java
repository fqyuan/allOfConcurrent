package com.fqyuan.atomicity;

import java.util.concurrent.atomic.AtomicInteger;

import com.fqyuan.share.EvenChecker;
import com.fqyuan.share.IntGenerator;

public class AtomicEvenGenerator extends IntGenerator {
	private AtomicInteger currentEvenValue = new AtomicInteger(0);

	@Override
	public int next() {
		return currentEvenValue.addAndGet(2);
	}

	public static void main(String[] args) {
		EvenChecker.test(new AtomicEvenGenerator());
	}
}
