package com.fqyuan.terminology;

class UnresponsiveUI {
	private volatile double d = 1;

	/*
	 * To make the program responsive, put the calculation inside a run() method
	 * to allow it to be preempted, and when you press the Enter key, you'll see
	 * that the calculation has indeed been running in the background while
	 * waiting for your user input.
	 */
	public UnresponsiveUI() throws Exception {
		while (d > 0)
			d = d + (Math.PI + Math.E) / d;
		System.in.read(); // Never gets here
	}
}

public class ResponsiveUI extends Thread {
	public static volatile double d = 1;

	public ResponsiveUI() {
		setDaemon(true);
		start();
	}

	@Override
	public void run() {
		while (true) {
			d = d + (Math.PI + Math.E) / d;
		}
	}

	public static void main(String[] args) throws Exception {
		// !Must kill this process
		// new UnresponsiveUI();
		new ResponsiveUI();
		System.in.read();
		System.out.println(d); // Shows progress
	}

}
