package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankAccount;
import bankapp.User;
import java.util.LinkedList;

class UserTests {
    
    private User user;
    private BankAccount firstAccount;
    private BankAccount secondAccount;
    private final String CORRECT_PASSWORD = "CorrectPassword";
    private final String INCORRECT_PASSWORD = "IncorrectPassword";

    @BeforeEach
    void setUp() {
        user = new User("hello", CORRECT_PASSWORD);
        firstAccount = new BankAccount(true, user, 100.0, 1);
        secondAccount = new BankAccount(true, user, 200.0, 2);
    }

    @Test
    void testGetPassword() {
        assertEquals(CORRECT_PASSWORD, user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword(INCORRECT_PASSWORD);
        assertEquals(INCORRECT_PASSWORD, user.getPassword());
    }

    @Test
    void testLoginSuccess() {
        assertTrue(user.login(CORRECT_PASSWORD));
    }

    @Test
    void testLoginFailure() {
        assertFalse(user.login(INCORRECT_PASSWORD));
    }

    @Test
    void testAddBankAccountSuccess() {
        assertTrue(user.addBankAccount(firstAccount));
        assertEquals(1, user.numberOfAccounts(CORRECT_PASSWORD));
    }

    @Test
    void testRemoveBankAccountSuccess() {
        user.addBankAccount(firstAccount);
        assertTrue(user.removeBankAccount(firstAccount));
        assertEquals(0, user.numberOfAccounts(CORRECT_PASSWORD));
    }

    @Test
    void testNumberOfAccountsSuccess() {
        user.addBankAccount(firstAccount);
        user.addBankAccount(secondAccount);
        assertEquals(2, user.numberOfAccounts(CORRECT_PASSWORD));
    }

    @Test
    void testNumberOfAccountsFailure() {
        user.addBankAccount(firstAccount);
        user.addBankAccount(secondAccount);
        assertEquals(0, user.numberOfAccounts(INCORRECT_PASSWORD));
    }

    @Test
    void testGetLiquidatedAssetsSuccess() {
        user.addBankAccount(firstAccount);
        user.addBankAccount(secondAccount);
        assertEquals(300.0, user.getLiquidatedAssets(CORRECT_PASSWORD));
    }

    @Test
    void testGetLiquidatedAssetsFailure() {
        user.addBankAccount(firstAccount);
        user.addBankAccount(secondAccount);
        assertEquals(0.0, user.getLiquidatedAssets(INCORRECT_PASSWORD));
    }
}

