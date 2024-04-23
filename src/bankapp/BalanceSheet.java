package bankapp;

import java.io.Serializable;
import java.util.LinkedList;

public class BalanceSheet implements Serializable {
	private boolean hasAssets;
	private User owner;
	private LinkedList<PersonalCapital> accounts;
	private static final long serialVersionUID = 1234122034233405813L;
	
	public BalanceSheet (User owner, boolean hasAssets) {
		this.owner = owner;
		this.hasAssets = hasAssets;
		this.accounts = new LinkedList<PersonalCapital>(); 
	}
	
	public LinkedList<PersonalCapital> getAccounts() {
		return accounts;
	}
	
	public void addAccount(PersonalCapital acct) {
		accounts.add(acct);
	}
	
	public boolean removeAccount(PersonalCapital acct) {
		return accounts.remove(acct);
	}

	public double getTotalLiquidValue() {
		double total = 0.0;
		
		for (PersonalCapital item: this.accounts) {
			total += item.getLiquidValue();
		}
		
		return total;
	}

	public boolean hasAssets() {
		return hasAssets;
	}
}
