package bankapp;

import java.io.Serializable;

public class BankAccount extends PersonalCapital implements Serializable {
	private static final long serialVersionUID = 12348350183405813L;
	private boolean isChecking;

	// Constructors - not tested
	public BankAccount(boolean isChecking, User owner, int ID) {
		this(isChecking, owner, 0.0, ID);
	}

	public BankAccount(boolean isChecking, User owner, double startBalance, int ID) {
		super(true, startBalance, owner, ID);
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
