package com.fqyuan.terminology;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * The above example allows you to set the handler on a case-by-case(就事论事) basis.
 * An even simpler approach is to set the default uncaught exception handler, which sets a static field inside the Thread class.
 */
public class SettingDefaultHandler {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());
	}
	/*
	 * This handler is only called if there is no per-thread uncaught exception
	 * handler. The system checks for a per-thread version, and if it doesn’t
	 * find one it checks to see if the thread group specializes its
	 * uncaughtException( ) method; if not, it calls the
	 * defaultUncaughtExceptionHandler.
	 */
}
