package bankapp;

public class Mortgage extends PersonalCapital {

	private double amount;
	private double interestRate;
	public int term;
	private double mortgagePayment;
	private final double lateFee = 0.03;
	private double amountPaid;
	private String name;

	public Mortgage(String name, double amount, double interestRate, int term, User owner) {
		super(false, amount, owner, term);
		setAmount(amount);
		this.interestRate = interestRate;
		this.term = term;
		this.amountPaid = 0;
		this.mortgagePayment = this.setMonthlyPayment();
		this.name = name;
	}

	public String toString(Mortgage m) {
		String s = "";
		s = "name: " + this.name + ", amount remaining: " + this.amount + ", years remaining: " + this.term
				+ ", interest rate: " + this.interestRate + ", amount paid this month: " + this.amountPaid;
		return s;
	}

	public String getName() {
		return this.name;
	}

	public double setMonthlyPayment() {
		double dividedInterest = this.interestRate / 12;
		double numerator = this.amount * dividedInterest;
		double denominator = (1 - Math.pow((1 + dividedInterest), -12 * (this.term)));
		double mortgagePayment = (numerator / denominator) - this.amountPaid;
		this.mortgagePayment = mortgagePayment - this.amountPaid;
		return this.mortgagePayment;
	}

	public void updateMonthlyMortgage(double money) {
		this.mortgagePayment -= money;
	}

	public double getMortgagePayment() {
		return this.mortgagePayment;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public int getTerm() {
		return term;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void payMortgage(double money, BankAccount account, boolean onTimeOrEarly, User currentUser) {
		this.setMonthlyPayment();
		double interest = getInterestPayment(); // this needs to go to the bank
		double total = interest + this.mortgagePayment;
		if (total >= this.amount) {
			if (money >= total) {
				double finalPayment = money - this.mortgagePayment;
				account.withdraw(finalPayment);
				this.amount = 0;
			}
		}
		if (money < total) {
			if (!onTimeOrEarly) {
				account.withdraw(money);
				this.amountPaid += money;
				this.amount -= money;
				this.mortgagePayment = (total - money) + setMonthlyPayment();
			} else {
				account.withdraw(money);
				this.amount -= money;
				this.amountPaid += money;
				this.updateMonthlyMortgage(money);
			}
		}

		if (money >= total) {
			account.withdraw(money);
			this.amount -= money;
			this.amountPaid = 0;
			this.updateMonthlyMortgage(total);
			setMonthlyPayment();
			if (!onTimeOrEarly) {
				this.amount = this.amount + (lateFee * this.interestRate * this.amount);
			}
		}

	}

	public double getInterestPayment() {
		return amount * (this.interestRate / 12);
	}

	public void incrementYear() {
		this.term--;
	}
}
