import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class deadlock {
	static void joinOrTimeout(Thread t, int milliseconds) {
		try {
			t.join(milliseconds);
		} catch (InterruptedException e) {
			System.err.println("Timed out: possible deadlock");
		}
	}
	public static void main(String[] args) {
		var account1 = new Account();
		var account2 = new Account();
		
		var t1 = new Thread(() -> Bank.transferMoney(account1, account2, 100.0));
		var t2 = new Thread(() -> Bank.transferMoney(account2, account1, 50.));
		// t1.start(); t2.start();
		// // timeout not working :v
		// joinOrTimeout(t1, 3000);

		t1 = new Thread(() -> Bank.transferWithDynamicOrder(account1, account2, 50.));
		t2 = new Thread(() -> Bank.transferWithDynamicOrder(account2, account1, 100.));
		t1.start(); t2.start();

		joinOrTimeout(t1, 1000);

		t1 = new Thread(() -> Bank.transferWithTryLock(account1, account2, 50.));
		t2 = new Thread(() -> Bank.transferWithTryLock(account2, account1, 100.));
		t1.start(); t2.start();

		joinOrTimeout(t1, 1000);
	}
}

class Bank {
	// transfer, thread-unsafe
	static void transfer(Account fromAcc,Account  toAcc, Double amount) {
		if (fromAcc.balance < amount) {
			// TODO: error
			return;
		}
		fromAcc.balance -= amount;
		toAcc.balance += amount;
		System.out.printf("from: %f, to: %f\n", fromAcc.balance, toAcc.balance);
	}

	static void sleepSafe(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			System.err.println("Sleep Interrupted");
		}
	} 

	// Can cause Left-Right Deadlock
	static void transferMoney(Account fromAcc, Account toAcc, Double amount) {
		synchronized(fromAcc) {
			sleepSafe(1000);
			synchronized(toAcc) {
				transfer(fromAcc, toAcc, amount);
			}
		}
	}

	// Dynamic Ordering prevents Left-Right-Deadlock
	static void transferWithDynamicOrder(Account fromAcc, Account toAcc, Double amount) {
		int fromHash = System.identityHashCode(fromAcc);
		int toHash = System.identityHashCode(toAcc);

		if (fromHash < toHash) {
			synchronized(fromAcc) {
				sleepSafe(1000);
				synchronized(toAcc) {
					transfer(fromAcc, toAcc, amount);
				}
			}
		} else {
			synchronized(toAcc) {
				sleepSafe(1000);
				synchronized(fromAcc) {
					transfer(fromAcc, toAcc, amount);
				}
			}
		}
	}

	static void transferWithTryLock(Account fromAcc, Account toAcc, Double amount) {
		boolean isTransferred = false;
		while (!isTransferred) {
			if (fromAcc.lock.tryLock()) {
				try {
					if (toAcc.lock.tryLock()) {
						try {
							transfer(fromAcc, toAcc, amount);
							isTransferred = true;
						} finally {
							toAcc.lock.unlock();
						}
					}
				} finally {
					fromAcc.lock.unlock();
				}
			}
		}
	}
}

class Account {
	Double balance = 200.;
	public final Lock lock = new ReentrantLock();
}
