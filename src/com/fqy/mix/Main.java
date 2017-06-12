package com.fqy.mix;

public class Main {
	public static void main(String[] args) {
		boolean result = false;
		try {
			result = ApplicationStartUpUtil.checkExternalServices();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Checking result is: " + result);
	}
}
