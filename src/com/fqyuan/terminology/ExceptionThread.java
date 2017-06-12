package com.fqyuan.terminology;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Encompassing the body of main within a try-catch block is unsuccessful
 */
public class ExceptionThread implements Runnable {
	public void run() {
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());
	}

}
