package bankapp;

public class BankAccount {
	
	private double balance;
	private int ID;
	private boolean isChecking;
	private User owner;
	
	//Constructors - not tested
	public BankAccount(boolean isChecking, User owner) {
		this.balance = 0;
		this.ID = (int) (Math.random() * 1000000);
		this.isChecking = isChecking;
		this.owner = owner;
	}
	
	public BankAccount(boolean isChecking, User owner, double startBalance) {
		this.balance = startBalance;
		this.ID = (int) (Math.random() * 1000000);
		this.isChecking = isChecking;
		this.owner = owner;
	}
	
	//public method doing some work - lots of tests
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		this.balance += amount;
	}
	
	public double withdraw(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		if(amount > this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
		this.balance -= amount;
		return amount;
	}
	
	public void transfer(double amount, BankAccount recipient) {
		if(amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		double transferAmt = this.withdraw(amount);
		recipient.deposit(transferAmt);
	}
	
	public double cashOut() {
		double cashOutAmt = this.balance;
		return this.withdraw(cashOutAmt);
	}
	
	//getters and setters - not tested
	public double getBalance() {
		return this.balance;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public boolean getIsChecking() {
		return this.isChecking;
	}
	
	public User getOwner() {
		return this.owner;
	}
}
