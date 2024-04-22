package bankapp;

import java.io.Serializable;

public class BankAccount extends PersonalCapital implements Serializable {
	private static final long serialVersionUID = 12348350183405813L;
	private int ID;
	private boolean isChecking;

	// Constructors - not tested
	public BankAccount(boolean isChecking, User owner) {
		this(isChecking, owner, 0.0);
	}

	public BankAccount(boolean isChecking, User owner, double startBalance) {
		super(true, startBalance, owner);
		checkStartBalance(startBalance);
		this.isChecking = isChecking;	
	}

	public boolean isChecking() {
		return this.isChecking;
	}

    public int LoanSetUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LoanSetUp'");
    }
}
