package com.fqy.mix;

public class GeneralInterrupt implements Runnable {
	public void run() {
		try {
			System.out.println("in run() - about to work()");
			work();
			System.out.println("in run() - back from work()");
		} catch (InterruptedException x) {
			System.out.println("in run() - interrupted in work()");
			return;
		}
		System.out.println("in run() - doing stuff after nap");
		System.out.println("in run() - leaving normally");
	}

	public void work2() throws InterruptedException {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("C isInterrupted()=" + Thread.currentThread().isInterrupted());
				Thread.sleep(2000);
				System.out.println("D isInterrupted()=" + Thread.currentThread().isInterrupted());
			}
		}
	}

	public void work() throws InterruptedException {
		while (true) {
			// Time consuming operation here.
			for (int i = 0; i < 1E7; i++) {
				// !Thread.currentThread().isInterrupted()

				// int j = i * 2;
				if (Thread.currentThread().isInterrupted())
					break;
				Math.sin(i);
			}
			/*
			 * Test whether this thread has been interrupted. The interrupted
			 * status of the thread is unaffected by the method. This is
			 * non-static method here.
			 */
			System.out.println("A isInterrupted()=" + Thread.currentThread().isInterrupted());
			/*
			 * Test whether the current thread has been interrupted. The
			 * interrupted status of the thread is cleared by the method. That's
			 * to say, the second call will return false. And this is a static
			 * method.
			 */
			if (Thread.interrupted()) {
				System.out.println("B isInterrupted()=" + Thread.currentThread().isInterrupted());
				throw new InterruptedException();
			}
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		GeneralInterrupt si = new GeneralInterrupt();
		Thread t = new Thread(si);
		t.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		System.out.println("in main() - interrupting other thread");
		t.interrupt();
		System.out.println("in main() - leaving");

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total time taken is: " + (endTime - startTime));
	}
}