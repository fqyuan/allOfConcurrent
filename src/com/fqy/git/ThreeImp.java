package com.fqy.git;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreeImp {

	public static void main(String[] args) {
		// 1. First way.
		A thread1 = new A();
		thread1.start();
		// 2. Second way.
		new Thread(new B()).start();
		// 3. Third way.
		C c = new C();
		// A FutureTask can be used to wrap a Callable or Runnable object.
		// Because FutureTask implements Runnable, a FutureTask can be submitted
		// to an Executor for execution.
		FutureTask<String> futureTask = new FutureTask<>(c);
		new Thread(futureTask).start();
		System.out.println("11111111111");
		try {
			// Waits if necessary for the computation to complete, and then
			// retrieves its result.
			System.out.println(futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("222222222");
	}

}

// Extends Thread
class A extends Thread {
	@Override
	public void run() {
		// This will not execute until futureTask.get() is executed!
		super.run();
		System.out.println("1--Extend class Thread!");
	}
}

// Implements Runnable
class B implements Runnable {
	@Override
	public void run() {
		System.out.println("2--Implement interface Runnable!");
	}
}

// The third way that i have no idea
/*
 * The Callable interface is similar to java.lang.Runnable, in that both are
 * designed for classes whose instances are potentially executed by another
 * thread. A Runnable, however, does not return a result and cannot throw a
 * checked exception.
 */
class C implements Callable<String> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call() A task that returns a result
	 * that may throw an exception.
	 */
	@Override
	public String call() throws Exception {
		System.out.println("Implemnt interface Callable!");
		return "success";
	}
}
