package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import bankapp.BankAccount;
import bankapp.Liabilities;
import bankapp.Mortgage;
import bankapp.PersonalCapital;
import bankapp.RetirementFund;
import bankapp.User;

public class RetirementFundTest {
	User x = new User(" ", " ");
	private RetirementFund test = new RetirementFund(0.05, 45000, x, 123);
	private BankAccount tester = new BankAccount(false, x, 100000.0, 234);
	

@Test
public void testGetValue() {
	test.addYearlyPayment(tester, 2000.0);
	assertEquals(2000.0, test.getValue(test)); 
}

@Test
public void testCalculateYearlyPaymentOwed() {
	assertEquals((0.05*45000), test.calculateYearlyPaymentOwed());
}

@Test 
public void testAddYearlyPayment() {
	test.addYearlyPayment(tester, 500);
	assertEquals(500, test.getPaidYearly()); 
}
	
}
