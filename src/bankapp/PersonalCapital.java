package bankapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class PersonalCapital implements Serializable {

		private boolean asset; 
		private double liquidValue; 
		private int increaseID = 1; 
		private int ID;//only used to generate hashcode for unique values
		private static final long serialVersionUID = -2305810380054L;
		
		
		public PersonalCapital (boolean asset, double liquidValue) {
			this.asset = asset; 
			this.liquidValue = liquidValue; 
			this.ID = increaseID++; 
		}
		
	

		@Override
		public int hashCode() {
			return Objects.hash(ID);
		}



		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PersonalCapital other = (PersonalCapital) obj;
			return ID == other.ID;
		}



		public int getID() {
			return this.hashCode();
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
			return list.remove(pc);
		}
		
		
	}


