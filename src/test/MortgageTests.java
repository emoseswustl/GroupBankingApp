package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;

import bankapp.BankAccount;
import bankapp.Liabilities;
import bankapp.Mortgage;
import bankapp.User;

public class MortgageTests {
	private User currentUser = new User (" ", "");
	private double testAmount = 400000;
	private double testInterest = 0.05;
	private int testYear = 30; 
	private Mortgage test = new Mortgage("", testAmount, testInterest, testYear);
	
	@Test
	public void objectConstruction() {
		assertEquals(testAmount, test.getAmount());
		assertEquals(testInterest, test.getInterestRate());
		assertEquals(testYear, test.getTerm());
	}
	
	@Test
	
	public void testLatePartialPay() {
		BankAccount account = new BankAccount(false, currentUser, 2000);
		test.payMortgage(1999, account, false, currentUser);
		double withdraw = account.getBalance();
		assertEquals(1, withdraw);
	}
	
	@Test
	public void testLateFullPay() {
		BankAccount acct2 = new BankAccount(false, currentUser, 3900);
		//they have enough
		test.payMortgage(3899, acct2, false, currentUser);
		//make sure it can be paid & a late fee is applied
		double paid = testAmount - 3899;
		double expected = paid + (0.03 * 0.05 * paid);
		assertEquals(expected, test.getAmount());
	}
	@Test
	public void testEarlyPartialPay() {
		BankAccount acct3 = new BankAccount(false, currentUser, 3900);
		//they have enough
		test.payMortgage(100, acct3, true, currentUser);
		//making sure a partial payment can be made towards a mortgage before deadline
		assertEquals(100, test.getAmountPaid());
		double expected = testAmount - 100;
		assertEquals(expected, test.getAmount(), 0.01);
		//making sure payment is reflected in amount
		assertEquals(2047.29, test.getMortgagePayment(), 0.01);
		//calculated expected manually using formula in setMonthlyPayment method
		assertEquals(3800, acct3.getBalance());
	}
	
@Test
	public void testEarlyFullPay() {
	BankAccount acct3 = new BankAccount(false, currentUser, 3900);
	//they have enough
	double testTotal = test.getMortgagePayment() + test.getInterestPayment();
	assertEquals(3813.96, testTotal, 0.01);
	//calculates monthly combined pay correctly
	test.payMortgage(3813.96, acct3, true, currentUser);
	double expected = 3900-3813.96;
	assertEquals(expected, acct3.getBalance(), 0.01);
	//account balance is withdrawn with proper about of money
	}

	
}



