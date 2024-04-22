package bankapp;

public class RetirementFund extends PersonalCapital {
	
	private double annualContributation; 
	private double income; 
	private double paidYearly;  

	
	public RetirementFund(double annualContributation, double income) {
		super(true, 0);
		this.annualContributation = annualContributation;
		this.income = income; 
		this.paidYearly = 0;
		
	}
	
	public void addYearlyPayment(BankAccount acct, double payment) {
		double total = calculateYearlyPaymentOwed() - this.paidYearly; 
		acct.withdraw(payment);
		super.liquidValue += payment;
		this.paidYearly += payment; 
		
	}
	
	public double calculateYearlyPaymentOwed(){
		return this.income * this.annualContributation;
	}
	
	public double getPaidYearly() {
		return this.paidYearly; 
	}
	
	public double getValue(RetirementFund retire) {
		return super.getLiquidValue(retire);
	}
	
	public void incrementYear() {
		this.paidYearly = 0; 
	}
	
}

