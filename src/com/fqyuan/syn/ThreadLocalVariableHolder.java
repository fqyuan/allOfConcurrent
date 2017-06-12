package com.fqyuan.syn;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Accessor implements Runnable {
	private final int id;

	public Accessor(int idn) {
		id = idn;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			ThreadLocalVariableHolder.increment();
			System.out.println(this);
			Thread.yield();
		}
	}

	public String toString() {
		return "#" + id + ": " + ThreadLocalVariableHolder.get();
	}
}

public class ThreadLocalVariableHolder {
	/*
	 * This class provides thread-local variables. These variables differ from
	 * their normal counterparts in that each thread that accesses one (via its
	 * get or set method) has its own, independently initialized copy of the
	 * variable. ThreadLocal instances are typically private static fields in
	 * classes that wish to associate state with a thread (e.g., a user ID or
	 * Transaction ID).
	 */
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		private Random rand = new Random(47);

		// Returns the current thread's "initial value" for this thread-local
		// variable.
		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}
	};

	/*
	 * Returns the value in the current thread's copy of this thread-local
	 * variable. If the variable has no value for the current thread, it is
	 * first initialized to the value returned by an invocation of the
	 * initialValue method.
	 */
	public static void increment() {
		value.set(value.get() + 1);
	}

	public static int get() {
		return value.get();
	}

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++)
			exec.execute(new Accessor(i));
		TimeUnit.MILLISECONDS.sleep(1); // Run for a while
		exec.shutdownNow(); // All Accessors will quit

		/*
		 * ThreadLocal objects are usually stored as static fields. When you
		 * create a ThreadLocal object, you are only able to access the contents
		 * of the object using the get( ) and set( ) methods. The get( ) method
		 * returns a copy of the object that is associated with that thread, and
		 * set( ) inserts its argument into the object stored for that thread,
		 * returning the old object that was in storage. The increment( ) and
		 * get( ) methods demonstrate this in ThreadLocalVariableHolder. Notice
		 * that increment( ) and get( ) are not synchronized, because
		 * ThreadLocal guarantees that no race condition can occur.
		 * 
		 */
	}
}