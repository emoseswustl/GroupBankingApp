package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;

import bankapp.BankAccount;
import bankapp.Mortgage;
import bankapp.User;

public class MortgageTests {
	private User x = new User("x", "x");
	private final double delta = 0.01; 
	
@Test 
	public void testSetMonthlyPayment() {
	double testAmount = 400000.00;
	double testInterest = 0.05; 
	int testYear = 30; 
	Mortgage test = new Mortgage(testAmount, testInterest, testYear);
//	assertEquals(0, test.getAmountPaid());
	assertEquals(400000.00, test.getAmount());
	assertEquals(test.getInterestRate(), 0.05);
	assertEquals(test.getTerm(), 30);
//	double expected = 2147.28; 
//	double actual = test.setMonthlyPayment();
//	double delta = 0.01;
//	assertEquals(expected, actual, delta);
}

@Test
	public void testLatePartialPay() {
		BankAccount account = new BankAccount(false, x, 2000);
		//testing individual who does not have enough to pay monthly mortgage
		double testAmount = 400000.0;
		double testInterest = 0.05; 
		int testYear = 30; 
		Mortgage test2 = new Mortgage(testAmount, testInterest, testYear);
		test2.payMortgage(1999, account, false);
		double withdraw = account.getBalance(); 
		assertEquals(1, withdraw);
		 
	}

@Test
	public void testLateFullPay() {
		BankAccount acct2 = new BankAccount(false, x, 3900);
		//they have enough
		double testAmount = 400000.0;
		double testInterest = 0.05; 
		int testYear = 30; 
		Mortgage test3 = new Mortgage(testAmount, testInterest, testYear);
		test3.payMortgage(3899, acct2, false);
		//make sure it can be paid & a late fee is applied
		double paid = testAmount - 3899; 
		double expected = paid + (0.03 * 0.05 * paid);
		assertEquals(expected, test3.getAmount());
	}

@Test
	public void testEarlyPartialPay() {
		BankAccount acct3 = new BankAccount(false, x, 3900);
		//they have enough
		double testAmount = 400000.0;
		double testInterest = 0.05; 
		int testYear = 30; 
		Mortgage test4 = new Mortgage(testAmount, testInterest, testYear);
		test4.payMortgage(100, acct3, true);
		//making sure a partial payment can be made towards a mortgage before deadline
		assertEquals(100, test4.getAmountPaid());
		double expected = testAmount - 100;
		assertEquals(expected, test4.getAmount(), delta);
		//making sure payment is reflected in amount
		assertEquals(2047.29, test4.getMortgagePayment(), delta); 
		//calculated expected manually using formula in setMonthlyPayment method 
		assertEquals(3800, acct3.getBalance());
	}
	
@Test
	public void testEarlyFullPay() {
	BankAccount acct3 = new BankAccount(false, x, 3900);
	//they have enough
	double testAmount = 400000.0;
	double testInterest = 0.05; 
	int testYear = 30; 
	Mortgage test4 = new Mortgage(testAmount, testInterest, testYear);
	double test = test4.getMortgagePayment() + test4.getInterestPayment();
	assertEquals(3813.96, test, delta);
	//calculates monthly combined pay correctly 
	test4.payMortgage(3813.96, acct3, true);
	double expected = 3900-3813.96;
	assertEquals(expected, acct3.getBalance(), delta);
	//account balance is withdrawn with proper about of money 
	}

@Test

public void testYear() {
	Mortgage x = new Mortgage(400, 0.4, 20);
	x.incrementYear();
	assertEquals(19, x.getTerm());
}

}