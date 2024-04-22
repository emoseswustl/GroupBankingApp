package bankapp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class BankDatabase {
	private Map<Integer, BankAccount> accounts;
	private Map<String, User> users;
	FileStorage storedAccounts;
	FileStorage storedUsers;
	
	public BankDatabase(String bankName) {
		this.accounts = new HashMap<Integer, BankAccount>();
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
	public void storeBank() {
		storedAccounts.writeMap(users);
		storedUsers.writeMap(accounts);
	}
	
	/**
	 * Adds a bank account to the database.
	 * @param account Account object to be added.
	 */
	public void addAccount(BankAccount account) {
		accounts.put(account.getID(), account);
	}
	
	/**
	 * Returns the account linked to a certain ID number.
	 * @param ID Account ID number.
	 * @return Account object.
	 */
	public BankAccount getAccount(int ID) {
		return accounts.get(ID);
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
		Collection<BankAccount> accountList = this.accounts.values();
		Set<Integer> accountNumbers = new HashSet<Integer>();
		for (BankAccount current: accountList) {
			if (!current.getOwner().getUsername().equals(username)) {
				accountNumbers.add(current.getID());
			}
		}
		return accountNumbers;
	}
	
	public boolean isAccountOwned(Integer accountID, String username) {
		BankAccount findAccount = this.accounts.get(accountID);
		if (findAccount != null) {
			return findAccount.getOwner().getUsername().equals(username);
		}
		return false;
	}
	
	public int createUniqueID() {
		BankAccount result = null;
		int newNumber = 0;
		while (result == null) {
			newNumber = (int) Math.random()*1000000;
			result = accounts.get(newNumber);
		}
		return newNumber;
	}
	
	public boolean isUsernameUnique(String name) {
		return (users.get(name) == null);
	}
}
