package bankapp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class BankDatabase {
	private Map<Integer, String> accounts;
	private Map<String, User> users;
	FileStorage storedAccounts;
	FileStorage storedUsers;
	
	public BankDatabase(String bankName) {
		this.accounts = new HashMap<Integer, String>();
        this.users = new HashMap<String, User>();
        this.storedAccounts = new FileStorage("accounts");
		this.storedUsers = new FileStorage("users");
	}
	
	/**
	 * Loads bank from files using the FileStorage class.
	 * @return A boolean indicating whether the transfer succeeded.
	 */
	public boolean loadBank() {
		if (storedAccounts.readBankAcctMap() != null && storedUsers.readUserMap() != null) {
			this.accounts = storedAccounts.readBankAcctMap();
			this.users = storedUsers.readUserMap();
			return true;
		}
		return false;
	}
	
	/**
	 * Writes bank information to files with the FileStorage class.
	 */
	public void saveBank() {
		storedAccounts.writeMap(users);
		storedUsers.writeMap(accounts);
	}
	
	/**
	 * Adds a financial account to the database.
	 * @param account PersonalCapital object to be added.
	 */
	public void addAccount(PersonalCapital account) {
		accounts.put(account.getID(), account.getOwner().getUsername());
		account.getOwner().addAsset(account);
	}
	
	public void addUser(User newUser) {
		users.put(newUser.getUsername(), newUser);
	}
	
	/**
	 * Returns the account linked to a certain ID number.
	 * @param ID Account ID number.
	 * @return Account object.
	 */
	public PersonalCapital getAccount(int ID) {
		User owner = users.get(accounts.get(ID));
		if (owner != null) {
			for (PersonalCapital current: owner.getAssetList()) {
				if (current.getID() == ID) {
					return current;
				}
			}
			for (PersonalCapital current: owner.getLiabilityList()) {
				if (current.getID() == ID) {
					return current;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the user linked to a specific username.
	 * @param username String representing the username requested.
	 * @return A user object containing all relevant information.
	 */
	public User getUser(String username) {
		return users.get(username);
	}
	
	/**
	 * Generates a list of all usernames for this bank.
	 * @return A set containing all usernames.
	 */
	public Set<String> getUserList() {
		return users.keySet();
	}
	
	/**
	 * Returns all accounts not owned by the user.
	 * @param username String representing the username.
	 * @return A set storing all account numbers that are not owned by the user.
	 */
	public Set<Integer> accountsNotOwned(String username) {
		Collection<Integer> accountList = this.accounts.keySet();
		Set<Integer> accountNumbers = new HashSet<Integer>();
		for (Integer current: accountList) {
			PersonalAsset acccount = 
			if (!current.equals(username)) {
				accountNumbers.add(current.getID());
			}
		}
		return accountNumbers;
	}
	
	/**
	 * Check if a certain account is owned by the user.
	 * @param accountID The account's ID number.
	 * @param username The string representing the user that could own the account.
	 * @return A boolean representing whether the user owns the account.
	 */
	public boolean isAccountOwned(Integer accountID, String username) {
		String accountOwner = this.accounts.get(accountID);
		if (accountOwner != null) {
			return accountOwner.equals(username);
		}
		return false;
	}
	
	/**
	 * Queries the database to ensure that a unique account number is being created.
	 * @return The account number as a six-digit integer.
	 */
	public int createUniqueID() {
		String owner = null;
		int newNumber = 0;
		while (owner == null) {
			newNumber = (int) Math.random()*1000000;
			owner = accounts.get(newNumber);
		}
		return newNumber;
	}
	
	/**
	 * Checks whether the username being provided is unique.
	 * @param name The user's potential name.
	 * @return A boolean that is true if the username does not exist in the users map.
	 */
	public boolean isUsernameUnique(String name) {
		return (users.get(name) == null);
	}
}
