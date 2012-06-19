package net.ion.bleujin.asyncrestlet;

public class Semaphore {
	private int value;

	/**
	 * Constructs the Semaphore.
	 * 
	 * @param value
	 *            initial value
	 */
	public Semaphore(int value) {
		this.value = value;
	}

	/**
	 * Constructs the Semaphore.
	 */
	public Semaphore() {
		this(0);
	}

	/**
	 * Acquires an amount from the semaphore. It there is not enough available the Thread is blocked.
	 * 
	 * @param n
	 *            the amount to acquire
	 */
	public synchronized void acquire(int n) {
		while (value < n) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		value -= n;
	}

	/**
	 * Acquires one from the Semaphore.
	 * 
	 * @see #acquire(int)
	 * 
	 */
	public void acquire() {
		acquire(1);
	}

	/**
	 * Releases the specified amount from the Semaphore.
	 * 
	 * @param n
	 *            the amount to release.
	 */
	public synchronized void release(int n) {
		value += n;

		notify();
	}
}
