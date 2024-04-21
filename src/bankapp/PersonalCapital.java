package bankapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class PersonalCapital  {

		private boolean asset; 
		public double liquidValue; 
		private int increaseID = 1; 
		private int ID;//only used to generate hashcode for unique values
		private static final long serialVersionUID = -2305810380054L;
	//	public User currentUser; 
		
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



		public long getID(PersonalCapital pc) {
			return pc.hashCode();
		}


		public double getLiquidValue(PersonalCapital pc) {
			if(pc.asset == true) {
				return this.liquidValue; 
			}
			else {
				return -1*this.liquidValue; 
			}
			
		}
		
		public double getTotalLiquidValue(User currentUser, boolean asset) {
			double total = 0.0; 
			if(asset == false) {
			for (PersonalCapital item : currentUser.l.liabilities) {
				total += item.getLiquidValue(item); 
			}
			}
			if(asset == true) {
			for(PersonalCapital item : currentUser.a.assets) {
				total += item.getLiquidValue(item);
			}
			}
			return total; 
		}
		
	
		
	}


