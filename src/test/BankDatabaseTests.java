package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankDatabase;
import bankapp.User;
import bankapp.PersonalCapital;

import java.util.LinkedList;

class BankDatabaseTests {
	BankDatabase database;
	User owner;

	@BeforeEach
	void setUp() {
		database = new BankDatabase();
		owner = new User("yay", "password");
	}

	@AfterEach
	void tearDown() {
		database.clearBank();
	}

	@Test
	void getName() {
		assertEquals(database.getName(), "TEST BANK");
	}
	
	@Test
	void ensureEmptyDatabase() {
		assertEquals(database.getUserList().size(), 0);
		assertEquals(database.getAllBankAccounts().size(), 0);
	}
	
	@Test
	void addAndGetUser() {
		database.addUser(owner);
		assertEquals(database.getUser(owner.getUsername()), owner);
		assertEquals(database.getUserList().size(), 1);
	}
	
	@Test
	void addAndGetPC() {
		database.addUser(owner);
		PersonalCapital account = new PersonalCapital(true, 50, owner, 99);
		owner.addAsset(account);
		database.addAccount(account);
		assertTrue(database.getUser(owner.getUsername()).getAssetList().contains(account));
		assertEquals(database.getAccount(account.getID()), account);
	}
	
	@Test
	void createUniqueBankAccounts() {
		LinkedList<PersonalCapital> accounts = new LinkedList<PersonalCapital>();
		for (int i = 0; i < 10000; i++) {
			PersonalCapital newAccount = new PersonalCapital(true, 50.0, owner, database.createUniqueID());
			accounts.add(newAccount);
			database.addAccount(newAccount);
		}
		assertEquals(accounts.size(), database.numberAccounts());
	}
	
	@Test
	void usernameNotUnique() {
		database.addUser(owner);
		assertFalse(database.isUsernameUnique(owner.getUsername()));
	}
	
	@Test
	void usernameIsUnique() {
		database.addUser(owner);
		assertTrue(database.isUsernameUnique("paper"));
	}
	
	@Test
	void removeUser() {
		database.addUser(owner);
		database.removeUser(owner);
		assertEquals(database.getUser(owner.getUsername()), null);
	}
	
	@Test
	void removeAccount() {
		database.addUser(owner);
		PersonalCapital account = new PersonalCapital(true, 50, owner, 99);
		database.addAccount(account);
		database.removeAccount(account);
		assertEquals(database.getAccount(account.getID()), null);
	}

}
