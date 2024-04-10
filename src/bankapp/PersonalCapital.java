package bankapp;

import java.util.HashMap;
import java.util.LinkedList;

public class PersonalCapital  {

		private boolean asset; 
		private double liquidValue; 
		private int ID; 
		
		
		public PersonalCapital (boolean asset, double liquidValue) {
			this.asset = asset; 
			this.liquidValue = liquidValue; 
			this.ID =(int) (Math.random() * 1000000);
		}
		
		public double getLiquidValue(PersonalCapital pc) {
			if(pc.asset == true) {
				return this.liquidValue; 
			}
			else {
				return -1*this.liquidValue; 
			}
			
		}
		
		public double getTotalLiquidValue(LinkedList<PersonalCapital>list) {
			double total = 0.0; 
			for (PersonalCapital item : list) {
				total += item.getLiquidValue(item); 
			}
			return total; 
		}
		
		public boolean addItem(PersonalCapital pc, LinkedList<PersonalCapital>list) {
			list.add(pc);
			int index = list.size() - 1; 
			if (list.get(index) == pc) {
				return true; 
			}
			else {
				return false; 
			}
		}
		public boolean removeItem(PersonalCapital pc, LinkedList<PersonalCapital>list) {
			list.remove(pc);
			int index = list.size() - 1; 
			if (list.get(index) == pc) {
				return false; 
			}
			else {
				return true; 
			}
		}
		
		
	}


