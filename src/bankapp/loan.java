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