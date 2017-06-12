package com.fqy.git;

import java.util.concurrent.CyclicBarrier;

public class Total {

	public static void main(String[] args) {
		//
		CyclicBarrier barrier = new CyclicBarrier(3, new TotalTask());
		new BillTask(barrier, "Beijing").start();
		new BillTask(barrier, "Shanghai").start();
		new BillTask(barrier, "Wuhan").start();
	}

}

class TotalTask implements Runnable {
	@Override
	public void run() {
		System.out.println("Start the Overall task.");
	}
}

class BillTask extends Thread {
	private CyclicBarrier barrier;
	private String code; // Used to stand for province code.

	BillTask(CyclicBarrier barrier, String code) {
		this.barrier = barrier;
		this.code = code;
	}

	public void run() {
		System.out.println("Start computing-- " + code + " province--data!");
		// Need to do...
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(code + " province has been complete,and tell it to the TotalTask");
		try {
			// Waits until all parties have invoked await on this barrier.
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}