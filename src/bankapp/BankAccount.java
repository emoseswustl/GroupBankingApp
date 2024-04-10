package bankapp;

public class BankAccount {

	private double balance;
	private int ID;
	private boolean isChecking;
	private final User owner; // if ownership of a BankAccount cannot/shouldn't be changed

	// Constructors - not tested
	public BankAccount(boolean isChecking, User owner) {
		this(isChecking, owner, 0.0);
	}

	public BankAccount(boolean isChecking, User owner, double startBalance) {
		checkStartBalance(startBalance);
		this.balance = startBalance;
		this.ID = (int) (Math.random() * 1000000);
		this.isChecking = isChecking;
		this.owner = owner;
	}

	private void checkStartBalance(double startBalance) {
		if (startBalance < 0)
			throw new IllegalArgumentException("Start balance must be non-negative");
	}

	// public method doing some work - lots of tests
	public void deposit(double amount) {
		validateAmountPositive(amount);
		synchronized (this) { // Prevent race-condition vulnerability/exploit ensures single thread access

			this.balance += amount;
		}
	}

	public double withdraw(double amount) {
		validateAmountPositive(amount);
		synchronized (this) {
			validateSufficientBalance(amount);
			this.balance -= amount;
		}
		return amount;
	}

	private void validateAmountPositive(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
	}

	private void validateSufficientBalance(double amount) {
		if (amount >= this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
	}

	public void transfer(double amount, BankAccount recipient) {
		validateTransfer(amount, recipient);
		synchronized (this) {
			this.withdraw(amount);
			recipient.deposit(amount);
		}
	}

	private void validateTransfer(double amount, BankAccount recipient) {
		if (recipient == null) {
			throw new IllegalArgumentException("Recipient can't be null");
		}
		if (recipient == this) {
			throw new IllegalArgumentException("Can't transfer to self");
		}
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		if (amount > this.balance) {
			throw new IllegalArgumentException("Amount must be less than or equal to balance");
		}
	}

	public double cashOut() {
		synchronized (this) {
			return this.withdraw(this.balance);
		}
	}

	public double getBalance() {
		return this.balance;
	}

	public int getID() {
		return this.ID;
	}

	public boolean isChecking() {
		return this.isChecking;
	}

	public User getOwner() {
		return this.owner;
	}



	import java.util.Scanner;

public class LoanSetUp {
    public static void main(String[] args) {
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
    }

    public static double calculateLoanPayment(double principal, double annualInterestRate, double years) {
        // Convert annual interest rate to monthly rate
        double monthlyInterestRate = annualInterestRate / 100 / 12;

        // Calculate the number of monthly payments
        int numPayments = (int) (years * 12);

        // Calculate the monthly payment using the formula for monthly loan payment
        double monthlyPayment = (principal * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numPayments));

        return monthlyPayment;
    }
}

}
