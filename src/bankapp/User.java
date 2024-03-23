package bankapp;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Objects;

public class User extends BankAccount { 
	LinkedList<BankAccount> user = new LinkedList<BankAccount>(); 
	private BankAccount bankaccount; 
	private String password; 

	public User(String password, LinkedList<BankAccount> user) {
		this.password = password; 
		this.user = user; 
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean login(String password, User x) {
		if (password == x.getPassword()) {
			return true; 
		}
		return false;
	}
	
	public LinkedList addBankAccount(LinkedList<BankAccount> user, BankAccount bankaccount, User x) { 
		boolean access = login(x.getPassword(), x); 
		if(access == true) {
			x.user.add(new BankAccount(bankaccount)); //call to other 
			return x.user; }
		else {
			return null; 
		}
	}
	
	public int numberOfAccounts(User x, LinkedList<BankAccount> user) {
		boolean access = login(x.getPassword(), x); 
		if(access == true) {
			return x.user.size(); 
		}
		else {
			return 0; //since account doesnt exist if login is not correct
		}
	}
	
	public LinkedList removeBankAccount(LinkedList<BankAccount> user, BankAccount bankaccount, User x) {
		boolean access = login(x.getPassword(), x); 
		if(access == true) {
		x.user.remove(bankaccount); 
		return x.user; }
		else {
			return null; }
	}
	
	public double getLiquidatedAssets(User x, LinkedList<BankAccount> user) {
		boolean access = login(x.getPassword(), x);
		int liquidatedAssets;
		if(access == true) {
			for (int i = 0; i<x.user.size(); i++) {
				liquidatedAssets += x.user.get(i.value()); //placeholder method being the amount in the certain asset
			}
			return liquidatedAssets; 
		}
		else {
			return 0;  
		}
	}
			
}





	
	
	

