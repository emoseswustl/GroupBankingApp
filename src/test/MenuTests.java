package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import bankapp.BankAccount;
import bankapp.Menu;
import bankapp.User;

public class MenuTests {

    private Menu menu;
    private ByteArrayOutputStream outputStream;
    private PrintStream startingOut;
    private InputStream startingIn;

	@BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        startingOut = System.out;
        System.setOut(new PrintStream(outputStream));
        startingIn = System.in;
        menu = new Menu(new Scanner(System.in));
    }

    @AfterEach
    public void destroyStreams() {
        System.setOut(startingOut);
        System.setIn(startingIn);
    }

    @Test
    public void testCreateUser() {
        User user = new User("Bob", "password");
        menu.createUser("Bob", user);
        assertEquals(user, menu.getUser("Bob"));
    }

    @Test
    public void testGetUser() {
        User user = new User("Bob", "password");
        menu.createUser("Bob", user);
        assertEquals(user, menu.getUser("Bob"));
    }

    @Test
    public void testProcessingUserDeposit() {
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.processingUserDeposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void testProcessingUserWithdraw() {
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.processingUserWithdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testMenuDeposit() {
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0);
        menu.setUser(user);
        user.addBankAccount(account);
        String input = "50.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuDeposit();
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void testMenuWithdraw() {
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0);
        menu.setUser(user);
        user.addBankAccount(account);
        String input = "50.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuWithdraw();
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testMenuTransfer() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0);
        BankAccount account2 = new BankAccount(true, user, 50.0);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        menu.setUser(user);
        String input = account1.getID() + "\n" + account2.getID() + "\n" + "25.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuTransfer();
        assertEquals(75.0, account1.getBalance());
        assertEquals(75.0, account2.getBalance());
    }

    @Test
    public void testMenuCheckBalance() {
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.menuCheckBalance();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Your balance is: 100.0"));
    }

    @Test
    public void testMenuAccountInformation() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0);
        BankAccount account2 = new BankAccount(false, user, 200.0);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        menu.setUser(user);
        menu.menuAccountInformation();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Account ID: " + account1.getID()));
        assertTrue(output.contains("Account Type: Checking"));
        assertTrue(output.contains("Account Balance: 100.0"));
        assertTrue(output.contains("Account ID: " + account2.getID()));
        assertTrue(output.contains("Account Type: Savings"));
        assertTrue(output.contains("Account Balance: 200.0"));
    }

    @Test
    public void testMenuSwitchAccounts() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0);
        BankAccount account2 = new BankAccount(false, user, 200.0);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        menu.setUser(user);
        menu.createUser("Bob", user);
        String input = account2.getID() + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuSwitchAccounts();
        assertEquals(account2, menu.getAccount());
    }

    @Test
    public void testMenuCreateAccount() {
        User user = new User("Bob", "password");
        menu.setUser(user);
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuCreateAccount();
        assertEquals(1, user.getBankAccounts().size());
        assertTrue(user.getBankAccounts().getFirst().isChecking());
    }

    @Test
    public void testMenuDeleteAccount() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0);
        BankAccount account2 = new BankAccount(false, user, 200.0);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        menu.setUser(user);
        menu.createUser("Bob", user);
        String input = account1.getID() + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        menu.menuDeleteAccount();
        assertEquals(2, user.getBankAccounts().size());
        assertEquals(account2, user.getBankAccounts().getFirst());
    }
}
