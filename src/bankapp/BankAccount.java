package bankapp;

import java.io.Serializable;

public class BankAccount extends PersonalCapital implements Serializable {
	private static final long serialVersionUID = 12348350183405813L;
	private boolean isChecking;

	public BankAccount(boolean isChecking, User owner, double startBalance, int ID) {
		super(true, startBalance, owner, ID);
		checkStartBalance(startBalance);
		this.isChecking = isChecking;	
	}

	public boolean isChecking() {
		return this.isChecking;
	}
}
