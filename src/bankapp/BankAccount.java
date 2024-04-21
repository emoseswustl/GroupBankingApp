package bankapp;

import java.io.Serializable;

public class BankAccount extends Assets implements Serializable {
	private static final long serialVersionUID = 12348350183405813L;
	private double balance;
	private int ID;
	private boolean isChecking;
	private final User owner; // if ownership of a BankAccount cannot/shouldn't be changed

	// Constructors - not tested
	public BankAccount(boolean isChecking, User owner) {
		this(isChecking, owner, 0.0);
	}

	public BankAccount(boolean isChecking, User owner, double startBalance) {
		super();
		checkStartBalance(startBalance);
		this.balance = startBalance;
		this.isChecking = isChecking;
		this.owner = owner;
	
	}

	private void checkStartBalance(double startBalance) {
		if (startBalance < 0)
			throw new IllegalArgumentException("Start balance must be non-negative");
	}

	// public method doing some work - lots of tests
	public void deposit(double amount) {
		validateAmountPositive(amount);
		synchronized (this) { // Prevent race-condition vulnerability/exploit ensures single thread access

			this.balance += amount;
		}
	}

	public double withdraw(double amount) {
		validateAmountPositive(amount);
		synchronized (this) {
			validateSufficientBalance(amount);
			this.balance -= amount;
		}
		return amount;
	}

	private void validateAmountPositive(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
	}

	private void validateSufficientBalance(double amount) {
		if (amount >= this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
	}

	public void transfer(double amount, BankAccount recipient) {
		validateTransfer(amount, recipient);
		synchronized (this) {
			this.withdraw(amount);
			recipient.deposit(amount);
		}
	}

	private void validateTransfer(double amount, BankAccount recipient) {
		if (recipient == null) {
			throw new IllegalArgumentException("Recipient can't be null");
		}
		if (recipient == this) {
			throw new IllegalArgumentException("Can't transfer to self");
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		if (amount > this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
	}

	public double cashOut() {
		synchronized (this) {
			return this.withdraw(this.balance);
		}
	}

	public double getBalance() {
		return this.balance;
	}

	public int getID() {
		return this.ID;
	}

	public boolean isChecking() {
		return this.isChecking;
	}

	public User getOwner() {
		return this.owner;
	}
}
