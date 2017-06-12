package com.fqyuan.atomicity;

public class SerialNumberGenerator {
	private static volatile int serialNumber = 0;

	public static /* synchronized should be here */ int nextSerialNumber() {
		return serialNumber++; // Not thread-safe
	}
}