package com.fqyuan.callable;

import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String> {
	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	/*
	 * Compute a result, or throw an exception if unable to do so
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() throws Exception {
		return "result of TaskWithResult " + id;
	}

}
