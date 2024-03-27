package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import bankapp.BankAccount;

class BankAccountTests {
	
	private BankAccount testAccount;
	private final double initialBalance = 100;

	@BeforeEach
	void setUp() {
		testAccount = new BankAccount(true, null, initialBalance);
	}

	@Test
	void testDeposit() {
		testAccount.deposit(50);
		assertEquals(initialBalance + 50, testAccount.getBalance(), 0.01);
	}

	@Test
	void testWithdraw() {
		testAccount.withdraw(50);
		assertEquals(initialBalance - 50, testAccount.getBalance(), 0.01);
	}

	@Test
	void testTransfer() {
		BankAccount recipient = new BankAccount(true, null, 0);
		testAccount.transfer(50, recipient);
		assertEquals(initialBalance - 50, testAccount.getBalance(), 0.01);
		assertEquals(50, recipient.getBalance(), 0.01);
	}

	@Test
	void testCashOut() {
		assertEquals(initialBalance, testAccount.cashOut(), 0.01);
		assertEquals(0, testAccount.getBalance(), 0.01);
	}

	@Test
	void testGetBalance() {
		assertEquals(initialBalance, testAccount.getBalance(), 0.01);
	}

	@Test
	void testGetID() {
		assertTrue(testAccount.getID() > 0);
	}

	@Test
	void testIsChecking() {
		assertTrue(testAccount.isChecking());
	}

	@Test
	void testOwner() {
		assertNull(testAccount.getOwner());
	}

	@Test
	void testSetOwner() {
		testAccount.setOwner("John Doe");
		assertEquals("John Doe", testAccount.getOwner());
	}

	@Test
	void testMultipleDeposits() {
		testAccount.deposit(50);
		testAccount.deposit(25);
		assertEquals(initialBalance + 75, testAccount.getBalance(), 0.01);
	}

	@Test
	void testMultipleWithdrawals() {
		testAccount.withdraw(50);
		testAccount.withdraw(25);
		assertEquals(initialBalance - 75, testAccount.getBalance(), 0.01);
	}

	@Test
	void testMultipleTransfers() {
		BankAccount recipient = new BankAccount(true, null, 0);
		testAccount.transfer(50, recipient);
		testAccount.transfer(25, recipient);
		assertEquals(initialBalance - 75, testAccount.getBalance(), 0.01);
		assertEquals(75, recipient.getBalance(), 0.01);
	}

	@Test
	void testMultipleCashOuts() {
		testAccount.cashOut();
		testAccount.cashOut();
		assertEquals(0, testAccount.getBalance(), 0.01);
	}

	@Test
	void testNegativeDeposit() {
		assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(-50));
	}

	@Test
	void testNegativeWithdraw() {
		assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(-50));
	}

	@Test
	void testOverdrawnWithdraw() {
		assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(initialBalance + 1));
	}

	@Test
	void testNegativeTransfer() {
		BankAccount recipient = new BankAccount(true, null, 0);
		assertThrows(IllegalArgumentException.class, () -> testAccount.transfer(-50, recipient));
	}

	@Test
	void testOverdrawnTransfer() {
		BankAccount recipient = new BankAccount(true, null, 0);
		assertThrows(IllegalArgumentException.class, () -> testAccount.transfer(initialBalance + 1, recipient));
	}
}
