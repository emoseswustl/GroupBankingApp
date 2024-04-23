package bankapp;

import java.io.Serializable;
import java.util.LinkedList;

public class Liabilities extends BalanceSheet implements Serializable {
	private static final long serialVersionUID = 1442940342133405813L;

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

	public int getTotalNumberofLiabilities() {
		return super.getAccounts().size();
	}
}
