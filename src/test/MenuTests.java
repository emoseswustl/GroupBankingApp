package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

	//@BeforeEach
    public void setUp(String input) {
        outputStream = new ByteArrayOutputStream();
        startingOut = System.out;
        System.setOut(new PrintStream(outputStream));
        startingIn = System.in;
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scan = new Scanner(inputStream);
        menu = new Menu(scan);
    }

    @AfterEach
    public void destroyStreams() {
        System.setOut(startingOut);
        System.setIn(startingIn);
    }

    @Test
    public void testCreateUser() {
    	setUp("");
        User user = new User("Bob", "password");
        menu.createUser("Bob", user);
        assertEquals(user, menu.getUser("Bob"));
    }

    @Test
    public void testGetUser() {
    	setUp("");
        User user = new User("Bob", "password");
        menu.createUser("Bob", user);
        assertEquals(user, menu.getUser("Bob"));
    }

    @Test
    public void testProcessingUserDeposit() {
    	setUp("");
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0, 1);
        menu.setUser(user);
        menu.setCurrentAccount(account);
        user.addBankAccount(account);
        menu.processingUserDeposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void testProcessingUserWithdraw() {
    	setUp("");
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0, 1);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.setCurrentAccount(account);
        menu.processingUserWithdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testMenuDeposit() {
    	setUp("50.0");
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0, 1);
        user.addBankAccount(account);
        menu.setUser(user);
        menu.setCurrentAccount(account);
        menu.menuDeposit();
        assertEquals(150.0, menu.getAccount().getBalance());
    }

    @Test
    public void testMenuWithdraw() {
    	setUp("50.0");
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0, 1);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.setCurrentAccount(account);
        menu.menuWithdraw();
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testMenuTransfer() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0, 1);
        BankAccount account2 = new BankAccount(true, user, 50.0, 22);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        setUp(account1.getID() + "\n" + account2.getID() + "\n" + "25.0\n");
        menu.setCurrentAccount(account1);
        menu.setUser(user);
        menu.menuTransfer();
        assertEquals(75.0, account1.getBalance());
        assertEquals(75.0, account2.getBalance());
    }

    @Test
    public void testMenuCheckBalance() {
    	setUp("");
        User user = new User("Bob", "password");
        BankAccount account = new BankAccount(true, user, 100.0, 1);
        menu.setUser(user);
        user.addBankAccount(account);
        menu.setCurrentAccount(account);
        menu.menuCheckBalance();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Your balance is: 100.0"));
    }

    @Test
    public void testMenuAccountInformation() {
    	setUp("");
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0, 1);
        BankAccount account2 = new BankAccount(false, user, 200.0, 2);
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
        BankAccount account1 = new BankAccount(true, user, 100.0, 1);
        BankAccount account2 = new BankAccount(false, user, 200.0, 2);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        setUp(account2.getID() + "\n");
        menu.setCurrentAccount(account2);
        menu.setUser(user);
        menu.createUser("Bob", user);
        menu.menuSwitchAccounts();
        assertEquals(account2, menu.getAccount());
    }

    @Test
    public void testMenuCreateAccount() {
    	setUp("1\n");
        User user = new User("Bob", "password");
        menu.setUser(user);
        menu.menuCreateAccount();
        assertEquals(1, user.getBankAccounts().size());
        assertTrue(user.getBankAccounts().getFirst().isChecking());
    }

    @Test
    public void testMenuDeleteAccount() {
        User user = new User("Bob", "password");
        BankAccount account1 = new BankAccount(true, user, 100.0, 1);
        BankAccount account2 = new BankAccount(false, user, 200.0, 2);
        user.addBankAccount(account1);
        user.addBankAccount(account2);
        setUp(account1.getID() + "\n");
        menu.setUser(user);
        menu.createUser("Bob", user);
        menu.menuDeleteAccount();
        assertEquals(2, user.getBankAccounts().size());
        assertEquals(account2, user.getBankAccounts().getFirst());
    }


@Test
public void testassets() {
	InputStream in = new ByteArrayInputStream("1\n".getBytes());
	User x = new User(" ", " ");
	x.assetsAndLiabilities(in);
			
	}
@Test
public void testLiabilities() {
	InputStream in = new ByteArrayInputStream("2\n".getBytes());
	User x = new User(" ", " ");
	x.assetsAndLiabilities(in);
}
@Test
public void testInvalid() {
	InputStream in = new ByteArrayInputStream("5\n3\n".getBytes());
	User x = new User(" ", " ");
	x.assetsAndLiabilities(in);
}
}
