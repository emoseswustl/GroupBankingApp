package bankapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class PersonalCapital {

	private boolean asset;
	public double liquidValue;
	private int increaseID = 1;
	private int ID;
	private User owner;
	private static final long serialVersionUID = -2305810380054L;

	public PersonalCapital(boolean asset, double liquidValue, User owner, int ID) {
		this.asset = asset;
		this.liquidValue = liquidValue;
		this.ID = ID;
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonalCapital other = (PersonalCapital) obj;
		return ID == other.ID;
	}

	public long getID() {
		return ID;
	}
	
	public double getLiquidValue() {
		if (this.asset == true) {
			return this.liquidValue;
		} else {
			return -1 * this.liquidValue;
		}
	}
	
	private void checkStartBalance(double startBalance) {
		if (startBalance < 0)
			throw new IllegalArgumentException("Start balance must be non-negative");
	}

	public void deposit(double amount) {
		validateAmountPositive(amount);
		synchronized (this) { // Prevent race-condition vulnerability/exploit ensures single thread access
			this.liquidValue += amount;
		}
	}

	public double withdraw(double amount) {
		validateAmountPositive(amount);
		synchronized (this) {
			validateSufficientBalance(amount);
			this.liquidValue -= amount;
		}
		return amount;
	}

	private void validateAmountPositive(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
	}

	private void validateSufficientBalance(double amount) {
		if (amount > this.liquidValue) {
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
		if (amount > this.liquidValue) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
	}

	public double cashOut() {
		synchronized (this) {
			return this.withdraw(this.liquidValue);
		}
	}
	
	public double getBalance() {
		return this.liquidValue;
	}
	
	public User getOwner() {
		return this.owner;
	}

}
