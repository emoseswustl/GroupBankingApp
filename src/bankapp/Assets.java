package bankapp;

import java.util.LinkedList;

public class Assets extends PersonalCapital {
	
	private LinkedList<PersonalCapital>assets = new LinkedList<>(); 
	
	public Assets (double liquidValue) {
		super(true, liquidValue); 
	}
	
	public boolean addItem(Assets a) {
		return super.addItem(a, assets); 
	}
	
	public boolean removeItem(Assets a) {
		return super.removeItem(a, assets);
	}
	
	public PersonalCapital get(int i) {
		return assets.get(i);
	}
	
	public double getTotalLiquidValue() {
		return super.getTotalLiquidValue(assets); 
	}
}



		

