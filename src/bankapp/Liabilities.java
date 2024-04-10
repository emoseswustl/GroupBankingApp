package bankapp;

import java.util.LinkedList;

public class Liabilities extends PersonalCapital {
		
		private LinkedList<PersonalCapital>liabilities = new LinkedList<>(); 
		
		public Liabilities (double liquidValue) {
			super(false, liquidValue); 
		}
		
		public boolean addItem(Liabilities l) {
			return super.addItem(l, liabilities); 
		}
		
		public boolean removeItem(Liabilities l) {
			return super.removeItem(l, liabilities);
		}
		
		public PersonalCapital get(int i) {
			return liabilities.get(i);
		}
		
		public double getTotalLiquidValue() {
			return -1 * (super.getTotalLiquidValue(liabilities)); 
		}
		
		public double getLiquidValue(Liabilities l) {
			return super.getLiquidValue(l);
		}
	}



