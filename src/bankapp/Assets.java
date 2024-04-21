package bankapp;

import java.util.LinkedList;

public class Assets extends PersonalCapital {
	
	public LinkedList<PersonalCapital>assets = new LinkedList<>(); 
	
	public Assets() {
		super(true, 0);
		this.assets = new LinkedList<PersonalCapital>(); 
	}
	
	public LinkedList<PersonalCapital> getAssetList(){
		return this.assets;	
	}
	
	
	public PersonalCapital get(int i) {
		return assets.get(i); 
	}
	
	public double getTotalLiquidValue(User currentUser) {
		return super.getTotalLiquidValue(currentUser, true); 
	}
	public int getTotalNumberofAssets() {
		return this.assets.size(); 
	}
}



		

