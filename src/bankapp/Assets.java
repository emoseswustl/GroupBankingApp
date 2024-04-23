package bankapp;

import java.io.Serializable;
import java.util.LinkedList;

public class Assets extends BalanceSheet implements Serializable {
	private static final long serialVersionUID = 2352463405813L;
		
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



		

