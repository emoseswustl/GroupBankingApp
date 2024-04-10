package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bankapp.FileStorage;
import bankapp.User;
import bankapp.BankAccount;

import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;

public class FileStorageTests {
	private FileStorage testFile;
	
	@Test
	void readEmptyFileMap() {
		FileStorage emptyFile = new FileStorage("nothing");
		HashMap<Integer, String> result = (HashMap<Integer, String>) emptyFile.readMap();
		assertEquals(result, null);
	}
	
	@Test
	void readWrite5KMap() {
		testFile = new FileStorage("test");
		HashMap<Integer, Character> write = new HashMap<Integer, Character>();
		char newValue = 'A';
		for (int i = 0; i < 5000; i++) {
			write.put(i, newValue);
			newValue++;
			if (newValue > 'z') {
				newValue = 'A';
			}
		}
		
		testFile.writeMap(write);
		File mapStored = testFile.getFile();
		assertTrue(mapStored.exists());
		assertTrue(mapStored.length() > 0);
		
		char compValue = 'A';
		HashMap<Integer, Character> read = (HashMap<Integer, Character>) testFile.readMap();
		for (int i = 0; i < 5000; i++) {
			char compare = read.get(i);
			assertEquals(compValue, compare);
			compValue++;
			if (compValue > 'z') {
				compValue = 'A';
			}
		}
	}
	
	@Test
	void readWriteUserMap() {
		testFile = new FileStorage("test");
		HashMap<String, User> userList = new HashMap<String, User>();
		String username = "A";
		for (int i = 0; i < 5000; i++) {
			User person = new User(username, "@");
			userList.put(username, person);
			username += (char) ('a' + i % 26);
		}
		
		testFile.writeMap(userList);
		File mapStored = testFile.getFile();
		assertTrue(mapStored.exists());
		assertTrue(mapStored.length() > 0);
		
		HashMap<String, User> readList = testFile.readUserMap();
		
		String afterUser = "A";
		for (int i = 0; i < 5000; i++) {
			User person = userList.get(afterUser);
			assertEquals(afterUser, person.getUsername());
			afterUser += (char) ('a' + i % 26);
		}
	}
	
	@Test
	void readWriteBankAcctMap() {
		testFile = new FileStorage("test");
		HashMap<Integer, BankAccount> accountList = new HashMap<Integer, BankAccount>();
		Integer ID = 1;
		String username = "A";
		
		for (int i = 0; i < 5000; i++) {
			User person = new User(username, "@");
			BankAccount newAcct = new BankAccount(false, person);
			accountList.put(ID, newAcct);
			ID++;
			username += (char) ('a' + i % 26);
		}
		
		testFile.writeMap(accountList);
		File mapStored = testFile.getFile();
		assertTrue(mapStored.exists());
		assertTrue(mapStored.length() > 0);
		
		HashMap<Integer, BankAccount> readList = testFile.readBankAcctMap();
		
		Integer IDNew = 1;
		String afterUser = "A";
		for (int i = 0; i < 5000; i++) {
			BankAccount result = accountList.get(IDNew);
			assertEquals(result.getOwner().getUsername(), afterUser);
			IDNew++;
			afterUser += (char) ('a' + i % 26);
		}
	}

}
