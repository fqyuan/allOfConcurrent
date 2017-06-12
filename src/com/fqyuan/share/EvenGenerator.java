package com.fqyuan.share;

/*
 * When threads collide.
 * This program will eventually fail because the EvenChecker tasks are able to access the information in EvenGenerator
 * tasks are able to access information in EvenGenerator while it's in an "incorrect" state.
 * This is part of the problem with multi-threaded programs-- they can appear to be correct even when there is a bug, if the 
 * probability for failure is very slow.
 */
public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	/*
	 * It's important that the increment operation itself requires multiple
	 * steps, and the task can be suspended by the threading mechanism in the
	 * midst of an increment-- that is, increment is not an atomic operation in
	 * Java. So even a single increment isn't safe to do without protecting the
	 * task.
	 */
	public int next() {
		++currentEvenValue; // Danger point here!
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator());
	}
}
