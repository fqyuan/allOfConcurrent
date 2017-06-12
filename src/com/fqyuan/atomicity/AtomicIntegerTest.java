package com.fqyuan.atomicity;

/*
 * In this program, 3 threads in total are here. 
 * Including:
 * 1).The main thread;
 * 2).The AtomicIntegerTest ait thread;
 * 3).The new Timer().schedule(TimeTask, long delay);
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Here we've eliminated the synchronized keyword by using AtomicInteger instead.
 * Because the program doesn't fail, a Timer is added to automatically abort after 5 seconds.
 */
public class AtomicIntegerTest implements Runnable {

	private AtomicInteger i = new AtomicInteger(0);

	public int getValue() {
		return i.get();
	}

	private void evenIncrement() {
		i.addAndGet(2);
	}

	@Override
	public void run() {
		while (true) {
			evenIncrement();
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		/*
		 * Create a new timer. The associated thread doesn't run as a daemon.
		 */
		/*
		 * java.util.Timer.scheduler(TimeTask task, long delay) Schedule the
		 * specified task for execution after the specified delay.
		 */
		new Timer().schedule(new TimerTask() {
			public void run() {
				System.err.println("Aborting");
				System.exit(0);
			}
		}, 3); // Terminate after 5 seconds
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicIntegerTest ait = new AtomicIntegerTest();
		exec.execute(ait);
		/*
		 * The following thread running is called the main-thread. so it will
		 * run with the use-specific thread. Another question? why will there be
		 * output, after print "Aborting"? Before the System.exit(0) is
		 * executed, cpu time is allocated to thread-main, so you get the output
		 * of main-thread! When the time is scheduled to the TimeTask which
		 * execute the System.exit(), the program eventually terminate.
		 */
		while (true) {
			int value = ait.getValue();
			/*
			 * The value got here may not be instantly updated: it may get the
			 * value from the cache or something.
			 */
			System.out.println(Thread.currentThread() + " :" + value);
			if (value % 2 != 0) {
				System.out.println(value);
				System.exit(0);
			}
		}

	}
}
