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
				"7. Create Account", "8. View Assets & Liabilities", "9. Delete Account", "10. Exit");
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
				assetsAndLiabilities(); 
				break;
			case 9:
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
	
	public void assetsAndLiabilities() {
		boolean validAnswer = false; 
		while(validAnswer == false) {
		System.out.println("Choose from the following options: ");
		System.out.println("1. Access Assets");
		System.out.println("2. Acess Liabilities");
		System.out.println("3. Exit");
		int option = caretaker.getInt(); 
		if(option == 1) {
			validAnswer = true;
			menuAssets(); 
		}
		else if (option == 2) {
			validAnswer = true;
			menuLiabilities(); 
		}
		else if (option == 3) {
			validAnswer = true; 
		}
		else {
			System.out.println("Invalid option. Please type 1, 2, or 3");
		}
	}
}
	
	public void menuAssets() {
		System.out.println("You are now viewing assets.");
		//will fix below line, discussing implementing the lists into bank account class
		System.out.println("Your total assets' liquid value is " + currentUser.getTotalLiquidValue());
		boolean exit = false; 
		while (exit != true) {
			System.out.println("What would you like to do next?");
			System.out.println("1. Get a list of current assets and values");
			System.out.println("2. View Retirement Fund");
			System.out.println("3. Exit");
			int option = caretaker.getInt(); 
			if(option == 1) {
				for(PersonalCapital pc : currentUser.assetList.assets) {
					int id = pc.getID();//fix ID method 
					Double val = pc.getLiquidValue(pc); 
					System.out.println(id + " value: " + val); 
				}
			}
			else if(option == 2) {
				System.out.println("What would you like to do with your retirement fund?");
				System.out.println("1. Deposit money");
				System.out.println("2. See balance");
				System.out.println("3. Open retirement fund");
				int opt = caretaker.getInt(); 
				if(opt == 1) {
					System.out.println("How much would you like to deposit?");
					double deposit = caretaker.getDouble(); 
					System.out.println("Which account would you like to pay from? Enter ID number: ");
					int idnumber = caretaker.getInt(); 
					//pseudocode: BankAccount acct = getAccount (idnumber)
					findFund().addYearlyPayment(acct, deposit); 
					}
				else if(opt == 2) {
					System.out.println("Total value of retirement fund is : " + findFund().getLiquidValue(findFund())); 
				}
				else if(opt == 3) {
					System.out.println("Which account would you like to pay from today?");
					//id needs to be fixed
					System.out.println("How much would you like to intialize?");
					double start = caretaker.getDouble();
					System.out.println("What is your average annual income?");
					double income = caretaker.getDouble(); 
					System.out.println("What is the annual rate you would like to contribute?");
					double rate = caretaker.getDouble(); 
					RetirementFund retirementfund = new RetirementFund(rate, income);
					currentUser.assetList.assets.add(retirementfund);
				}
				
			} 
			else if(option == 3) {
				exit = true; 
				}
			
			else {
				System.out.println("Please select 1, 2, or 3");
			}
		}
	}

public RetirementFund findFund() {
	for(int i = 0; i < currentUser.assetList.assets.size(); i++) {
		if(currentUser.assetList.assets.get(i) instanceof RetirementFund) {
			return (RetirementFund)currentUser.assetList.assets.get(i);
		}
	}
	return null; 
}

		public void menuLiabilities() {
			System.out.println("You are now viewing liabilities.");
			//will fix below line, discussing implementing the lists into bank account class
			System.out.println("Your total liabilities' liquid value is " + currentUser.getTotalLiquidValue(liabilities));
			boolean exit = false; 
			while (exit != true) {
				System.out.println("What would you like to do next?");
				System.out.println("1. Get a list of current liabilities and values");
				System.out.println("2. Remove a liability");
				System.out.println("3. Add a liability");
				System.out.println("4. Access loans");
				System.out.println("5. Access mortgage");
				System.out.println("6. Exit");
				int option = caretaker.getInt(); 
				if(option == 1) {
					for(PersonalCapital pc : currentUser.getLiabilities()) {
						int id2 = pc.getID();//fix ID method 
						Double val = pc.getLiquidValue(pc); 
						System.out.println(id2 + " value: " + val); 
					}
				}
				else if(option == 2) {
					System.out.println("Which liabilitiy would you like to remove? Enter ID number");
					int idnum = caretaker.getInt(); 
					boolean success = false; 
					for(PersonalCapital x : currentUser.liabilityList.liabilities) {
						if(x.getID() == idnum) { //fix ID method 
							 success = currentUser.liabilityList.liabilities.remove(x); 
						}
					}
					if(success == true) {
						System.out.println("Item removed");
					}
					else {
						System.out.println("Item not removed"); 
					}
				} 
				else if(option == 3) {
					System.out.println("Which type of liability would you like to add?");
					System.out.println("1. Mortgage");
					System.out.println("2. Loan");
					int op = caretaker.getInt(); 
					if(op == 1) {
						System.out.println("What is the name of your Mortgage?");
						String name = caretaker.getString(); 
						System.out.println("What is the total amount you need to pay?");
						double due = caretaker.getDouble(); 
						System.out.println("What is the interest rate of your Mortgage?");
						double rate = caretaker.getDouble(); 
						System.out.println("How many years is your mortgage?");
						int years = caretaker.getInt(); 
						Mortgage newM = new Mortgage(name, due, rate, years);
						currentUser.liabilityList.liabilities.add(newM);
					}
					else if(op == 2) {
						//need to add with loan attributes 
					}
				}
				else if(option == 4) {
					//need to discuss with how loans are supposed to be implemented 
				}
				else if(option == 5) {
					System.out.println("You are accessing mortgages.");
					int count = 0; 
					for(int i = 0; i < currentUser.liabilityList.liabilities.size(); i++) {
						Object Mortgage;
						if((currentUser.liabilityList.liabilities.get(i)).equals(Mortgage)) {
							count++; 
							System.out.println(count + "." + currentUser.liabilityList.liabilities.get(i).toString());
							
						}
					}
					boolean exit2 = false; 
					System.out.println("Total Mortgages: " + count);
					while(exit2 == false) {
					System.out.println("What would you like to do?");
					System.out.println("1. Pay Mortgage");
					System.out.println("2. See Mortgage Payment Due this Month");
					System.out.println("3. Exit");
					int select = caretaker.getInt(); 
					if(select == 1) {
						System.out.println("What mortgage would you like to pay? Select number from list abbove");
						int wow = caretaker.getInt();
						Mortgage paying = (Mortgage)currentUser.liabilityList.liabilities.get(wow);
						double total = paying.getInterestPayment() + paying.getMortgagePayment(); 
						System.out.println("Your mortgage due this month is: " + total + ". How much are you paying today?");
						double money = caretaker.getDouble(); 
						if(money < total) {
							System.out.println("You are paying less than tht total due for this month. If the rest of the payment is turned in after the due date, a late fee will be added to your account. Proceed?");
							System.out.println("Type 'yes' to continue");
							String choice = caretaker.getString(); 
							if(choice.equals("yes")) {	
							}
							else {
								exit = true; 
							}
						}
						if(exit == true) {
						} else {
						boolean answer = false; 
						boolean time = false; 
						while (answer == false) {
						System.out.println("Did client turn in payment before or after deadline? Type True for before, False for after");
						String ans = caretaker.getString(); 
						if(ans.equals("True")) {
							answer = true; 
							time = true; 
						}
						else if (ans.equals("False")) {
							answer = true; 
							time = false; 
						}
						else {
							System.out.println("Inavlid Answer.");
						}
					}
						System.out.println("Enter the account number  that the user is paying with");
						Integer id = caretaker.getInt(); 
						BankAccount pay = currentUser.getBankAccounts().get(id);
						double before = pay.getBalance(); 
						paying.payMortgage(money, pay, time, currentUser); 
						if(before - money == pay.getBalance()) {
							System.out.println("Successfully paid. Total overall amount remaining: " + paying.getAmount());
						}
						else {
							System.out.println("Unsucessful payment. Please check account balance, mortgage, and payment amount.");
						}
						}
				}
					else if(option == 2) {
						System.out.println("What mortgage would you like to see? Select number from list abbove");
						int wow = caretaker.getInt();
						Mortgage view = (Mortgage)currentUser.liabilityList.liabilities.get(wow);
						double total = view.getInterestPayment() + view.getMortgagePayment(); 
						System.out.println("Payment of " + total + " due to this month");
					}
					else if (option == 3){
						exit = true;
					}
					else {
						System.out.println("Please selection option 1-3.");
					}
				}
			}
				else if(option == 6) {
					exit = true; 
				}
				else {
					System.out.println("Invalid option. Please selection an option 1-6.");
				}
				
			}
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