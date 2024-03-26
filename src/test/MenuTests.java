package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.stefanbirkner.systemlambda.SystemLambda;

import bankapp.BankAccount;
import bankapp.Menu;

class MenuTests {

	@Test
	void testUserDeposit() throws Exception {
		Menu test = new Menu();
		test.processingUserSelection(50);
		BankAccount account = test.getAccount();
		assertEquals(50, account.getBalance(), 0.01);
	}

	@Test
	void testDisplayingOptions() throws Exception {
		String output = SystemLambda.catchSystemOut(() -> {
			Menu test = new Menu();
			test.displayingOptions();
		});
		assertTrue(output.contains("How much money do you want to deposit?"));
	}

	@Test
	void testGetUserInputPositive() throws Exception {
		double inputAmount = SystemLambda.withTextFromSystemIn("50").execute(() -> {
			Menu test = new Menu();
			return test.getValidUserInput();
		});
		assertEquals(50, inputAmount, 0.01);
	}

	@Test
	void testGetUserInputNegativeThenPositive() throws Exception {
		String consoleOutput = SystemLambda.tapSystemOut(() -> {
            double capturedInput = SystemLambda.withTextFromSystemIn("-50\n100\n")
                .execute(() -> {
                    Menu m = new Menu();
                    return m.getValidUserInput();
                });
            assertEquals(100, capturedInput, 0.01);
        });
        assertTrue(consoleOutput.contains("Invalid value!"));
    }

	@Test
	void testProcessingUserSelection() throws Exception {
		String output = SystemLambda.catchSystemOut(() -> {
			Menu test = new Menu();
			test.processingUserSelection(50);
		});
		assertTrue(output.contains("Your balance is now: 50.0"));
	}

	@Test
	void testUserInputZero() throws Exception {
		double inputAmount = SystemLambda.withTextFromSystemIn("0\n").execute(() -> {
			Menu test = new Menu();
			return test.getValidUserInput();
		});
		assertEquals(0, inputAmount, 0.01);
	}

	@Test
	void testLargeInputValue() throws Exception {
		double inputAmount = SystemLambda.withTextFromSystemIn("999999999\n").execute(() -> {
			Menu test = new Menu();
			return test.getValidUserInput();
		});
		assertEquals(999999999, inputAmount, 0.01);
	}

	@Test
	void testMultipleInputs() throws Exception {
		SystemLambda.withTextFromSystemIn("100\n200\n").execute(() -> {
			Menu test = new Menu();
			test.getValidUserInput();
			double secondInput = test.getValidUserInput();
			assertEquals(200, secondInput, 0.01);
		});
	}

	@Test
	void testNonNumericInput() throws Exception {
		String output = SystemLambda.tapSystemOut(() -> {
			SystemLambda.withTextFromSystemIn("abc\n50\n").execute(() -> {
				Menu test = new Menu();
				return test.getValidUserInput();
			});
		});
		assertTrue(output.contains("Invalid value!"));
	}

	@Test
	void testBalanceAfterDepositsWithdrawals() throws Exception {
		Menu test = new Menu();
		SystemLambda.withTextFromSystemIn("100\n200\n").execute(() -> {
			test.processingUserSelection(100);
			test.processingUserSelection(200);
		});
		test.getAccount().withdraw(50);
		assertEquals(250, test.getAccount().getBalance(), 0.01);
	}

	@Test
	void testPromptsNonNumeric() throws Exception {
		String output = SystemLambda.tapSystemOut(() -> {
			SystemLambda.withTextFromSystemIn("notANumber\n100\n").execute(() -> {
				Menu test = new Menu();
				test.getValidUserInput();
			});
		});
		assertTrue(output.contains("How much money do you want to deposit?"), "Should re-prompt on non-numeric input.");
	}

	@Test
	void testNegativeInput() throws Exception {
		String output = SystemLambda.tapSystemOut(() -> {
			SystemLambda.withTextFromSystemIn("-1\n50\n").execute(() -> {
				Menu test = new Menu();
				return test.getValidUserInput();
			});
		});
		assertTrue(output.contains("Invalid value!"), "Error message for negative input should be displayed.");
	}

	@Test
	void testInteraction() throws Exception {
		String output = SystemLambda.tapSystemOut(() -> {
			SystemLambda.withTextFromSystemIn("100\n").execute(() -> {
				Menu test = new Menu();
				test.displayingOptions();
				test.getValidUserInput();
				test.processingUserSelection(100);
			});
		});
		assertTrue(output.contains("Your balance is now: 100.0"), "Complete interaction should result in correct balance.");
	}

	@Test
	void testMultipleInvalidInputs() throws Exception {
		String output = SystemLambda.tapSystemOut(() -> {
			SystemLambda.withTextFromSystemIn("-100\nabc\n50\n").execute(() -> {
				Menu test = new Menu();
				return test.getValidUserInput();
			});
		});
		assertTrue(output.contains("Invalid value!") && output.endsWith("50.0"), "Should handle multiple invalid inputs before accepting a valid one.");
	}
}
