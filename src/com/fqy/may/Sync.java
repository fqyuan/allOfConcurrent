package com.fqy.may;

public class Sync {
	public static void main(String[] args) {
		Example example = new Example();
		for (int i = 0; i < 10; i++) {
			new Thread(new Counter(example)).start();
		}
	}
}

class Counter implements Runnable {
	private Example example;

	public Counter(Example example) {
		// TODO Auto-generated constructor stub
		this.example = example;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(example.getNextValue());
	}

}

class Example {
	private volatile int value = 0;

	public int getNextValue() {
		return value++;
	}

}