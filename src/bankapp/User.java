package bankapp;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Objects;
import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -2305810380492L;
	private LinkedList<BankAccount> userAccounts;
	private String password;
	private String username;
	private Liabilities liabilityList; 
	private Assets assetList; 

	public User(String username, String password) {
		this.userAccounts = new LinkedList<BankAccount>();
		this.username = Objects.requireNonNull(username, "Username must be non-null");
		this.password = Objects.requireNonNull(password, "Password must be non-null");
		this.liabilityList = new Liabilities();
		this.assetList = new Assets();
	}
	
	public LinkedList<PersonalCapital> getLiabilities() {
		return this.liabilityList.getLiabilityList();
	}
	
	public LinkedList<PersonalCapital> getAssets() {
		return this.assetList.getAssetList();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = Objects.requireNonNull(username, "Username must be non-null");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Objects.requireNonNull(password, "Password must be non-null");
	}

	public boolean login(String password) {
		return this.password.equals(Objects.requireNonNull(password, "Password must be non-null"));
	}

	public boolean addBankAccount(BankAccount bankaccount) {
		userAccounts.add(Objects.requireNonNull(bankaccount, "BankAccount must be non-null"));
		return true;
	}
	
	public LinkedList<BankAccount> getBankAccounts() {
		return userAccounts;
	}

	public boolean removeBankAccount(BankAccount bankaccount) {
		return userAccounts.remove(Objects.requireNonNull(bankaccount, "BankAccount must be non-null"));
	}

	public int numberOfAccounts(String password) {
		if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			return userAccounts.size();
		}
		return 0;
	}

	public double getLiquidatedAssets(String password) {
		double liquidatedAssets = 0.0;
		if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			for (BankAccount bankaccount : userAccounts) {
				liquidatedAssets += bankaccount.getBalance();
			}
		}
		return liquidatedAssets;
	}

    public int LoanSetUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LoanSetUp'");
    }

}