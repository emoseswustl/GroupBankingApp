package bankapp;

public class BankAccount {
	
	private double balance;
	private int ID;
	private boolean isChecking;
	private final User owner; // if ownership of a BankAccount cannot/shouldn't be changed
	
	//Constructors - not tested
	public BankAccount(boolean isChecking, User owner) {
		this(isChecking, owner, 0.0);
	}
	
	public BankAccount(boolean isChecking, User owner, double startBalance) {
		if (startBalance < 0) {
			throw new IllegalArgumentException("Start balance must be non-negative");
		}
		this.balance = startBalance;
		this.ID = (int) (Math.random() * 1000000);
		this.isChecking = isChecking;
		this.owner = owner;
	}
	
	//public method doing some work - lots of tests
	public void deposit(double amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		synchronized(this) { // Prevent race-condition vulnerability/exploit ensures single thread access
			this.balance += amount;
		}
	}
	
	public double withdraw(double amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		synchronized (this) { 
			if(amount > this.balance) {
				throw new IllegalArgumentException("Amount must be less than or equal to balance");
			}
			this.balance -= amount;
		}
		return amount;
	}
	
	public void transfer(double amount, BankAccount recipient) {
		if (recipient == null) {
			throw new IllegalArgumentException("Recipient can't be null");
		}
		if (recipient == this) {
			throw new IllegalArgumentException("Can't transfer to self");
		}
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		if(amount > this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
		synchronized (this) {
			this.withdraw(amount);
			recipient.deposit(amount);
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
