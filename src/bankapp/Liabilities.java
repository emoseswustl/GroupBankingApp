package bankapp;

import java.util.LinkedList;

public class Liabilities extends BalanceSheet {

	public Liabilities(User currentUser) {
		super(currentUser, false);
	}

	public LinkedList<PersonalCapital> getLiabilityList() {
		return super.getAccounts();
	}

	public PersonalCapital get(int i) {
		return super.getAccounts().get(i);
	}

	public double getTotalLiquidValue(User currentUser) {
		return super.getTotalLiquidValue();
	}

	public double getLiquidValue(PersonalCapital l) {
		return l.getLiquidValue();
	}

	public int getTotalNumberofLiabilities() {
		return super.getAccounts().size();
	}
}
