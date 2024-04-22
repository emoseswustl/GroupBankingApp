package bankapp;

import java.util.LinkedList;

public class Assets extends BalanceSheet {
		
	public Assets(User currentUser) {
		super(currentUser, true);
	}
	
	public LinkedList<PersonalCapital> getAssetList(){
		return getAccounts();	
	}
	
	public PersonalCapital get(int i) {
		return getAccounts().get(i); 
	}
	
	public double getTotalLiquidValue(User currentUser) {
		return super.getTotalLiquidValue(); 
	}
	
	public int getTotalNumberofAssets() {
		return getAccounts().size(); 
	}
}



		

