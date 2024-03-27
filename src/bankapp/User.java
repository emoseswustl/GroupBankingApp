package bankapp;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Objects;

public class User { 
	private LinkedList<BankAccount> userAccounts;  
	private String password; 

	public User(String password) {
		this.userAccounts = new LinkedList<BankAccount>();
		this.password = password; 
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean login(String password) {
		return this.password.equals(password);
	}
	
	public boolean addBankAccount(BankAccount bankaccount, String password) {  
		if(login(password)) {
			userAccounts.add(bankaccount);
			return true; 
		} 
		return false;
	}

	public boolean removeBankAccount(BankAccount bankaccount, String password) {
		if(login(password)) {
			return userAccounts.remove(bankaccount);
		}
		return false;
	}
	
	public int numberOfAccounts(String password) {
		if(login(password)) {
			return userAccounts.size(); 
		}
		return 0;
	}
	
	public double getLiquidatedAssets(String password) {
		double liquidatedAssets = 0.0;
		if(login(password)) {
			for (BankAccount bankaccount : userAccounts) {
				liquidatedAssets += bankaccount.getBalance();
			}
		}
		return liquidatedAssets;
	}	
}