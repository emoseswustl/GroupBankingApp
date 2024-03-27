package bankapp;

import java.util.Scanner;

import bankapp.User;

import bankapp.BankAccount;

interface InputCaretaker {
	double getDouble();
}

class ScannerCaretaker implements InputCaretaker {
	private Scanner scanner;

	public ScannerCaretaker(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public double getDouble() {
		return scanner.nextDouble();
	}
}

public class Menu {

	private InputCaretaker caretaker;
	private BankAccount account;
	private User user;

	
	//Constructor
	public Menu(InputCaretaker caretaker) {
		this.caretaker = caretaker;
		user = new User("abc");
		account = new BankAccount(true, user, 0.0);
	}

	//not tested
	public static void main(String[] args) {
		Menu mainMenu = new Menu(new ScannerCaretaker(new Scanner(System.in)));
		mainMenu.displayingOptions();
		double amount = mainMenu.getValidUserInput();
		mainMenu.processingUserSelection(amount);
	}
	
	//Code that just displays stuff - no tests needed
	public void displayingOptions() {
		System.out.println("How much money do you want to deposit?");
	}
	
	//Code that gets user input
	//No tests needed...for now (probably discuss in future class)
	public double getValidUserInput() {
		double amount = caretaker.getDouble();
		while(amount < 0) {
			System.out.println("Invalid value!");
			System.out.println("How much money do you want to deposit?");
			amount = caretaker.getDouble();
		}
		return amount;
	}
	
	//Does work - needs tests
	public void processingUserSelection(double amount) {
		account.deposit(amount);
		System.out.println("Your balance is now: " + account.getBalance());
	}
	
	public BankAccount getAccount() {
		return account;
	}
}