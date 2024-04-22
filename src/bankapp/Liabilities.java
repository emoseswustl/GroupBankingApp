package bankapp;

import java.util.LinkedList;

public class Liabilities extends PersonalCapital {
		
		public LinkedList<PersonalCapital>liabilities = new LinkedList<>(); 
		
		
		public Liabilities () {
			super(false, 0);
			this.liabilities = new LinkedList<PersonalCapital>(); 
		}
		
		public LinkedList<PersonalCapital> getLiabilityList(){
			return this.liabilities;	
		}
		
		public PersonalCapital get(int i) {
			return liabilities.get(i);
		}
		
		public double getTotalLiquidValue(User currentUser) {
			return super.getTotalLiquidValue(currentUser, false); 
		}
		
		public double getLiquidValue(Liabilities l) {
			return super.getLiquidValue(l);
		}
		public int getTotalNumberofLiabilities() {
			return liabilities.size(); 
		}
	}



