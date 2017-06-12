package com.fqy.mix;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {

	public static void main(String[] args) {
		DeadLockDemoUtil.demonstrate();
	}

}

class DeadLockDemoUtil {
	public static void demonstrate() {
		AccountOpr accountOpr = new AccountOpr();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					accountOpr.stageOne();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					accountOpr.stageTwo();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		accountOpr.afterTransferring();
	}
}

class AccountOpr {
	Account account1 = new Account();
	Account account2 = new Account();
	ReentrantLock lock1 = new ReentrantLock();
	ReentrantLock lock2 = new ReentrantLock();
	Random random = new Random();

	public void stageOne() throws InterruptedException {
		for (int i = 0; i < 1E5; i++) {
			// We can also adjust the sequence to avoid circular waiting here.
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(account1, account2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void stageTwo() throws InterruptedException {
		for (int i = 0; i < 1E5; i++) {
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(account2, account1, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void afterTransferring() {
		System.out.println("After transferring, account1 remained: " + account1.getBalance());
		System.out.println("After transferring, account2 remained: " + account2.getBalance());
		System.out.println("After transferring, account1 && account2 totally have: "
				+ (account1.getBalance() + account2.getBalance()));
	}

	// Hold and wait condition doesn't exist here.
	private void acquireLocks(ReentrantLock lock1, ReentrantLock lock2) throws InterruptedException {
		boolean lockStatus1 = false;
		boolean lockStatus2 = false;
		while (true) {
			try {
				lockStatus1 = lock1.tryLock();
				lockStatus2 = lock2.tryLock();
			} finally {
				if (lockStatus1 && lockStatus2)
					return; // acquire both
				if (lockStatus1)
					lock1.unlock(); // acquire lock1
				if (lockStatus2)
					lock2.unlock(); // acquire lock2
			}
			Thread.sleep(1); // acquire neither
		}
	}
}

class Account {
	private int balance = 10000;

	private void depoist(int val) {
		balance += val;
	}

	private void withdraw(int val) {
		balance -= val;
	}

	public int getBalance() {
		return balance;
	}

	// Used as the util function for the class.
	public static void transfer(Account account1, Account account2, int val) {
		account1.withdraw(val);
		account2.depoist(val);
	}
}