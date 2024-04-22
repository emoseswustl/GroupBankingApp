package bankapp;

import java.util.LinkedList;

public class BalanceSheet {
	private boolean hasAssets;
	private User owner;
	private LinkedList<PersonalCapital> accounts;
	
	public BalanceSheet (User owner, boolean hasAssets) {
		this.owner = owner;
		this.hasAssets = hasAssets;
		this.accounts = new LinkedList<PersonalCapital>(); 
	}
	
	public LinkedList<PersonalCapital> getAccounts() {
		return accounts;
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
