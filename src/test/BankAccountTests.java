import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankAccount;

class BankAccountTests {

    private BankAccount testAccount;

    @BeforeEach
    void setup() {
        testAccount = new BankAccount();
    }

    @Test
    void testInitialization() {
        assertEquals(0.0, testAccount.getBalance(), 0.01, "New account should have 0 balance");
    }

    @Test
    void testSimpleDeposit() {
        testAccount.deposit(25);
        assertEquals(25.0, testAccount.getBalance(), 0.01, "Depositing 25 should increase balance to 25");
    }

    @Test
    void testNegativeDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(-25));
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    void testGetBalance() {
        testAccount.deposit(200);
        assertEquals(200.0, testAccount.getBalance(), 0.01, "Balance should be 200 after depositing 200");
    }
}
