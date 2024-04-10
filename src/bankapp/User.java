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

	public User(String username, String password) {
		this.userAccounts = new LinkedList<BankAccount>();
		this.username = Objects.requireNonNull(username, "Username must be non-null");
		this.password = Objects.requireNonNull(password, "Password must be non-null");
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
		//if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			userAccounts.add(Objects.requireNonNull(bankaccount, "BankAccount must be non-null"));
			return true;
		//}
		//return false;
	}
	
	public LinkedList<BankAccount> getBankAccounts() {
		return userAccounts;
	}

	public boolean removeBankAccount(BankAccount bankaccount) {
		//if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			return userAccounts.remove(Objects.requireNonNull(bankaccount, "BankAccount must be non-null"));
		//}
		//return false;
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

}