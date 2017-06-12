package com.fqyuan.blog;

public class Main {

	public static void main(String[] args) {
		boolean flag = LatchUtil.isServicesReady();
		System.out.println("Checking result is: " + flag);
	}

}
