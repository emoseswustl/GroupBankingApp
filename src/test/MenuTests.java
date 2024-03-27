package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.Menu;
import bankapp.InputCaretaker;

class InputCaretakerMocker implements InputCaretaker {
	private double inputValue;

	public InputCaretakerMocker(double inputValue) {
		this.inputValue = inputValue;
	}

	@Override
	public double getDouble() {
		return inputValue;
	}
}

class MenuTests {

	private BankAccount account;
	private Menu menu;

	@BeforeEach
	void setUp() {
		InputCaretaker InputCaretakerMocker = new InputCaretakerMocker(100.0);
		menu = new Menu(InputCaretakerMocker);
		account = menu.getAccount();
	}

	@Test
	void testDeposit() {
		menu.processingUserSelection(50.0);
		assertEquals(50.0, account.getBalance(), 0.01);
	}

	@Test
	void testMultipleDeposits() {
		menu.processingUserSelection(50.0);
		menu.processingUserSelection(25.0);
		assertEquals(75.0, account.getBalance(), 0.01);
	}

	@Test
	void testNegativeDeposit() {
		menu.processingUserSelection(-50.0);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	void testZeroDeposit() {
		menu.processingUserSelection(0.0);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	void testGetPositiveUserInput() {
		double input = menu.getValidUserInput();
		assertEquals(100.0, input, 0.01);
	}

	@Test
	void testGetNegativeUserInput() {
		menu = new Menu(new InputCaretakerMocker(-100.0));
		double input = menu.getValidUserInput();
		assertEquals(-100.0, input, 0.01);
	}

	@Test
	void testGetZeroUserInput() {
		menu = new Menu(new InputCaretakerMocker(0.0));
		double input = menu.getValidUserInput();
		assertEquals(0.0, input, 0.01);
	}
}
