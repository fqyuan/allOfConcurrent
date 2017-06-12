package com.fqyuan.daemon;

import java.util.concurrent.ThreadFactory;

/*
 * ThreadFactory: An object that creates new threads on demand.
 */
public class DaemonThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}

}
