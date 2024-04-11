package bankapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private InputCaretaker caretaker;
	
	private HashMap<Integer, BankAccount> accounts;
	private HashMap<String, User> users;
	
	private List<Integer> accountIDs;
	
	private BankAccount currentAccount;
	private User currentUser;
	private Boolean firstIteration = true;
	private FileStorage bankAccts;
	private FileStorage userAccts;
	
	public Menu(Scanner scanner) {
        this(new ScannerCaretaker(scanner));
        this.accounts = new HashMap<Integer, BankAccount>();
        this.users = new HashMap<String, User>();
    }
	// Constructor
	public Menu(InputCaretaker caretaker) {
        this.caretaker = caretaker;
        this.accounts = new HashMap<Integer, BankAccount>();
        this.users = new HashMap<String, User>();
        this.accountIDs = new LinkedList<Integer>();
    }

	// not tested
	public static void main(String[] args) {
		runMainMenu();
	}

	private static void runMainMenu() {
		Menu mainMenu = new Menu(new ScannerCaretaker(new Scanner(System.in)));
		while (true) {
			if (mainMenu.firstIteration) {
				mainMenu.initizalizeBank();
			}
			getExecuteOptions(mainMenu);
		}
	}

	private static void getExecuteOptions(Menu mainMenu) {
		mainMenu.displayingOptions();
		int option = mainMenu.getOption();
		while (option < 1 || option > 10) {
			System.out.println("Invalid option!");
			mainMenu.displayingOptions();
			option = mainMenu.getOption();
		}
		if (option == 10) {
			System.out.println("Exiting...");
			mainMenu.userAccts.writeMap(mainMenu.users);
			mainMenu.bankAccts.writeMap(mainMenu.accounts);
			System.exit(0);
		}
		mainMenu.executeSelectedOption(option);
		mainMenu.firstIteration = false;
	}
	
	private void printUsers() {
		System.out.println("List of users: ");
		for (User person: users.values()) {
			System.out.println(person.getUsername());
		}
		System.out.println();
	}

	private void initizalizeBank() {
		bankAccts = new FileStorage("accounts");
		userAccts = new FileStorage("users");
		if (bankAccts.readBankAcctMap() != null && userAccts.readUserMap() != null) {
			System.out.println("Database files loaded! \n");
			accounts = bankAccts.readBankAcctMap();
			users = userAccts.readUserMap();
			
			printUsers();
			String userSelect = "";
			while (users.get(userSelect) == null) {
				if (userSelect.equals("new")) {
					createNewAccounts();
					return;
				}
				System.out.println("Enter a valid username, or type \"new\" to make a new account: ");
				userSelect = getString();
			}
			String password = "";
			while (!users.get(userSelect).getPassword().equals(password)) {
				System.out.println("Enter your password: ");
				password = getString();
			}
			currentUser = users.get(userSelect);
			currentAccount = currentUser.getBankAccounts().getFirst();
		} else {
			createNewAccounts();
		}
	}
	
	private void getLoadMaps() {
		bankAccts = new FileStorage("accounts");
		userAccts = new FileStorage("users");
		if (bankAccts.readBankAcctMap() != null && userAccts.readUserMap() != null) {
			accounts = bankAccts.readBankAcctMap();
			users = userAccts.readUserMap();
		}
	}
	
	private void createNewAccounts() {
		displayFirstIterationName();
		String name = getString();
		displayFirstIterationPassword();
		String password = getString();
		displayFirstIterationEnd();
		User user = new User(name, password);
		createUser(name, user);
		setUser(user);
		currentAccount = new BankAccount(true, user, 10000);
		user.addBankAccount(currentAccount);
	}

	public void setUser(User user) {
		this.currentUser = user;
	}
	
	public void createUser(String name, User user) {
		users.put(name, user);
	}
	
	public User getUser(String name) {
		return users.get(name);
	}

	// Code that just displays stuff - no tests needed
	public void displayingOptions() {
		List<String> options = Arrays.asList("Choose from the following options: ", "1. Deposit", "2. Withdraw",
				"3. Transfer", "4. Check Balance", "5. Show Account Information", "6. Switch Account",
				"7. Create Account", "8. Delete Account", "9. Loans", "10. Exit");
		for (String option : options) {
			System.out.println(option);
		}
	}

	public void executeSelectedOption(int option) {
		System.out.println("You have selected option: " + option);
		switch (option) {
			case 1:
				menuDeposit();
				break;
			case 2:
				menuWithdraw();
				break;
			case 3:
				menuTransfer();
				break;
			case 4:
				menuCheckBalance();
				break;
			case 5:
				menuAccountInformation();
				break;
			case 6:
				menuSwitchAccounts();
				break;
			case 7:
				menuCreateAccount();
				break;
			case 8:
				menuDeleteAccount();
				break;
			case 9:
			   menuLoans();
		}
	}

	public void menuDeposit() {
		System.out.println("How much money do you want to deposit?");
		double amount = getValidUserDeposit();
		processingUserDeposit(amount);
	}

	public void menuWithdraw() {
		System.out.println("How much money do you want to withdraw?");
		double amount = getValidUserWithdraw();
		processingUserWithdraw(amount);
	}

	public void menuTransfer() {
		if (currentUser.getBankAccounts().size() < 2) {
			System.out.println("You need at least two accounts to transfer money.");
			return;
		}

		System.out.println("Choose from the following account IDs to transfer from: ");
		for (BankAccount account : currentUser.getBankAccounts()) {
			System.out.println("Account ID: " + account.getID());
		}

		int senderID = getOption();
		BankAccount senderAccount = null;
		for (BankAccount account : currentUser.getBankAccounts()) {
			if (account.getID() == senderID) {
				senderAccount = account;
				break;
			}
		}

		while (senderAccount == null) {
			System.out.println("Invalid account ID!");
			System.out.println("Choose from the following account IDs to transfer from: ");
			for (BankAccount account : currentUser.getBankAccounts()) {
				System.out.println("Account ID: " + account.getID());
			}
			senderID = getOption();
			for (BankAccount account : currentUser.getBankAccounts()) {
				if (account.getID() == senderID) {
					senderAccount = account;
					break;
				}
			}
		}

		System.out.println("Choose from the following account IDs to transfer to: ");
		for (BankAccount account : currentUser.getBankAccounts()) {
			if (account != senderAccount) {
				System.out.println("Account ID: " + account.getID());
			}
		}

		int recipientID = getOption();
		BankAccount recipientAccount = null;
		for (BankAccount account : currentUser.getBankAccounts()) {
			if (account.getID() == recipientID && account != senderAccount) {
				recipientAccount = account;
				break;
			}
		}

		while (recipientAccount == null) {
			System.out.println("Invalid account ID!");
			System.out.println("Choose from the following account IDs to transfer to: ");
			for (BankAccount account : currentUser.getBankAccounts()) {
				if (account != senderAccount) {
					System.out.println("Account ID: " + account.getID());
				}
			}
			recipientID = getOption();
			for (BankAccount account : currentUser.getBankAccounts()) {
				if (account.getID() == recipientID && account != senderAccount) {
					recipientAccount = account;
					break;
				}
			}
		}

		System.out.println("Enter the amount to transfer: ");
		double amount = getValidUserWithdraw();

		senderAccount.transfer(amount, recipientAccount);

		System.out.println("Transfer successful!");
		System.out.println("Sender Account Balance: " + senderAccount.getBalance());
		System.out.println("Recipient Account Balance: " + recipientAccount.getBalance());
	}

	public void menuCheckBalance() {
		System.out.println("Your balance is: " + currentAccount.getBalance());
	}

	public void menuAccountInformation() {
		System.out.println("Account Information: ");
		for (BankAccount account : currentUser.getBankAccounts()) {
			System.out.println("Account ID: " + account.getID());
			System.out.println("Account Type: " + (account.isChecking() ? "Checking" : "Savings"));
			System.out.println("Account Balance: " + account.getBalance());
		}
	}

	public void menuSwitchAccounts() {
		if (accounts.size() < 2) {
			System.out.println("You need at least two accounts to switch accounts");
			return;
		}
		System.out.println("Choose the account ID to switch to: ");
		accountIDs.clear();
		for (BankAccount account: currentUser.getBankAccounts()) {
			System.out.println("Account ID: " + account.getID());
			accountIDs.add(account.getID());
		}
		int switchID = getOption();
		while (!accountIDs.contains(switchID)) {
			System.out.println("Invalid account ID!");
			switchID = getOption();
		}
		currentAccount = accounts.get(accountIDs.indexOf(switchID));
	}

	public void menuCreateAccount() {
		System.out.println("Do you want to create a (1) checking account or a (2) savings account? ");
		int accountType = getOption();
		while (accountType != 1 && accountType != 2) {
			System.out.println("Invalid account type!");
			System.out.println("Do you want to create a (1) checking account or a (2) savings account? ");
			accountType = getOption();
		}
		System.out.println("Creating account...");
		if (accountType == 1) {
			BankAccount checking = new BankAccount(true, currentUser, 0.0);
			addAccount(checking);
		} else {
			BankAccount savings = new BankAccount(false, currentUser, 0.0);
			addAccount(savings);
		}
	}

	public void menuDeleteAccount() {
		if (accounts.size() < 1) {
			System.out.println("You need at least one account to delete an account");
			return;
		}
		System.out.println("Choose from the following account IDs to delete an account: ");
		accountIDs.clear();
		for (BankAccount account: currentUser.getBankAccounts()) {
			System.out.println("Account ID: " + account.getID());
			accountIDs.add(account.getID());
		}
		int deleteID = getOption();
		while (!accountIDs.contains(deleteID)) {
			System.out.println("Invalid account ID!");
			System.out.println("Choose from the following account IDs to delete an account: ");
			for (BankAccount account : currentUser.getBankAccounts()) {
				System.out.println("Account ID: " + account.getID());
			}
			deleteID = getOption();
		}
		BankAccount toRemove = accounts.get(deleteID);
		currentUser.removeBankAccount(toRemove);
		accounts.remove(deleteID);
	}
	

// payment cal
public static double calculateLoanPayment(double principal, double annualInterestRate, double years) {
	// Convert annual interest rate to monthly rate
	double monthlyInterestRate = annualInterestRate / 100 / 12;

	// Calculate the number of monthly payments
	int numPayments = (int) (years * 12);

	// Calculate the monthly payment using the formula for monthly loan payment
	double monthlyPayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numPayments));

	return monthlyPayment;
}
 // Loan set up method 
 public double setupLoan() {
	Scanner scanner = new Scanner(System.in);

	// User input for loan details
	System.out.print("Enter the loan amount: $");
	double loanAmount = scanner.nextDouble();

	System.out.print("Enter the annual interest rate (as a percentage): ");
	double annualInterestRate = scanner.nextDouble();

	System.out.print("Enter the loan term in years: ");
	double loanTermYears = scanner.nextDouble();

	// Calculate the monthly loan payment
	double monthlyPayment = calculateLoanPayment(loanAmount, annualInterestRate, loanTermYears);

	// Display the monthly payment to the user
	System.out.printf("Your monthly loan payment is: $%.2f%n", monthlyPayment);

	scanner.close();
	return monthlyPayment;
}

public double menuLoans(){
	return setupLoan(); 
}

	public int getOption() {
		return caretaker.getInt();
	}

	


	public void displayFirstIterationName() {
		System.out.println("Welcome to the bank!");
		System.out.println("Enter your name: ");
	}

	public void displayFirstIterationPassword() {
		System.out.println("Enter your password: ");
	}

	public void displayFirstIterationEnd() {
		System.out.println("Thank you for entering your name and password!");
		System.out.println("You can now proceed to the main menu. Have Fun!");
	}

	public String getString() {
		return caretaker.getString();
	}

	// Code that gets user input
	// No tests needed...for now (probably discuss in future class)
	public double getValidUserDeposit() {
		double amount = caretaker.getDouble();
		while (amount <= 0) {
			System.out.println("Invalid value!");
			System.out.println("How much money do you want to deposit?");
			amount = caretaker.getDouble();
		}
		return amount;
	}

	public double getValidUserWithdraw() {
		double amount = caretaker.getDouble();
		while (amount <= 0 || amount > currentAccount.getBalance()) {
			System.out.println("Invalid value!");
			System.out.println("How much money do you want to withdraw?");
			amount = caretaker.getDouble();
		}
		return amount;
	}

	public void processingUserWithdraw(double amount) {
		currentAccount.withdraw(amount);
		System.out.println("Your balance is now: " + currentAccount.getBalance());
	}

	// Does work - needs tests
	public void processingUserDeposit(double amount) {
		currentAccount.deposit(amount);
		System.out.println("Your balance is now: " + currentAccount.getBalance());
	}
	
	public void addAccount(BankAccount account) {
		currentUser.addBankAccount(account);
		accounts.put(account.getID(), account);
	}
	
	public void setCurrentAccount(BankAccount account) {
		this.currentAccount = account;
	}

	public BankAccount getAccount() {
		return this.currentAccount;
	}
}