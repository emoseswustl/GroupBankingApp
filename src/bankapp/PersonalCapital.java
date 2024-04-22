package bankapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class PersonalCapital {

	private boolean asset;
	public double liquidValue;
	private int increaseID = 1;
	private int ID;
	private User owner;
	private static final long serialVersionUID = -2305810380054L;

	public PersonalCapital(boolean asset, double liquidValue, User owner) {
		this.asset = asset;
		this.liquidValue = liquidValue;
		this.ID = increaseID++;
		this.owner = owner;
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

	public long getID() {
		return ID;
	}

	public double getLiquidValue(PersonalCapital pc) {
		if (pc.asset == true) {
			return this.liquidValue;
		} else {
			return -1 * this.liquidValue;
		}

	}

	public double getTotalLiquidValue(boolean asset) {
		double total = 0.0;
		LinkedList<PersonalCapital> accounts;
		
		if (asset) {
			accounts = owner.getAssets();
		} else {
			accounts = owner.getLiabilities();
		}
		
		for (PersonalCapital item: accounts) {
			total += item.getLiquidValue(item);
		}
		
		return total;
	}
	
	public double getNetWorth() {
		double total = 0.0;
		LinkedList<PersonalCapital> accounts;
		
		if (asset) {
			accounts = owner.getAssets();
		} else {
			accounts = owner.getLiabilities();
		}
		
		for (PersonalCapital item: accounts) {
			total += item.getLiquidValue(item);
		}
		
		return total;
	}

}
