package bankapp;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Objects;
import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -2305810380492L;
	//private LinkedList<BankAccount> userAccounts;
	private String password;
	private String username;
	private Liabilities liabilityList; 
	private Assets assetList; 

	public User(String username, String password) {
		//this.userAccounts = new LinkedList<BankAccount>();
		this.username = Objects.requireNonNull(username, "Username must be non-null");
		this.password = Objects.requireNonNull(password, "Password must be non-null");
		this.liabilityList = new Liabilities(this);
		this.assetList = new Assets(this);
	}
	
	public LinkedList<PersonalCapital> getLiabilityList() {
		return this.liabilityList.getLiabilityList();
	}
	
	public LinkedList<PersonalCapital> getAssetList() {
		return this.assetList.getAssetList();
	}
	
	public Liabilities getLiabilities() {
		return liabilityList;
	}
	
	public Assets getAssets() {
		return assetList;
	}
	
	public boolean addAsset(PersonalCapital asset) {
		return assetList.addAccount(asset);
	}
	
	public boolean addLiability(PersonalCapital liability) {
		return liabilityList.addAccount(liability);
	}
	
	public boolean removeAsset(PersonalCapital asset) {
		return assetList.removeAccount(asset);
	}
	
	public boolean removeLiability(PersonalCapital liability) {
		return liabilityList.removeAccount(liability);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = Objects.requireNonNull(username, "Username must be non-null");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Objects.requireNonNull(password, "Password must be non-null");
	}

	public boolean login(String password) {
		return this.password.equals(Objects.requireNonNull(password, "Password must be non-null"));
	}
	
	public LinkedList<BankAccount> getBankAccounts() {
		LinkedList<BankAccount> userAccounts = new LinkedList<BankAccount>();
		
		for (PersonalCapital account: getAssetList()) {
			if(account instanceof BankAccount) {
				userAccounts.add((BankAccount) account);
			}
		}
		
		return userAccounts;
	}

	public int numberOfAccounts(String password) {
		if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			return getAssetList().size();
		}
		return 0;
	}

	public double getLiquidatedAssets(String password) {
		double liquidatedAssets = 0.0;
		if (login(Objects.requireNonNull(password, "Password must be non-null"))) {
			liquidatedAssets = getAssetBalance();
		}
		return liquidatedAssets;
	}
	
	public RetirementFund findFund() {
		for(int i = 0; i < assetList.getAssetList().size(); i++) {
			if(assetList.getAssetList().get(i) instanceof RetirementFund) {
				return (RetirementFund) assetList.getAssetList().get(i);
			}
		}
		return null; 
	}

	public double getNetWorth() {
		double total = 0.0;		
		
		for (PersonalCapital item: this.getAssetList()) {
			total += item.getLiquidValue();
		}
		
		for (PersonalCapital item: this.getLiabilityList()) {
			total += item.getLiquidValue();
		}
		
		return total;
	}
	
	public double getAssetBalance() {
		return assetList.getTotalLiquidValue();
	}
	
	public double getLiabilityBalance() {
		return liabilityList.getTotalLiquidValue();
	}
	
    public int LoanSetUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LoanSetUp'");
    }

}