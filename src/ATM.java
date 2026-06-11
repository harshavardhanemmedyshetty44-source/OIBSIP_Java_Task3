import java.text.DecimalFormat;

public class ATM {
    private double balance;
    public static final DecimalFormat df = new DecimalFormat("₹0.00");

    public ATM(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative: " + df.format(initialBalance));
        }
        this.balance = initialBalance;
    }

    public String checkBalance() {
        return "YOUR BALANCE: " + df.format(balance);
    }

    public String deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive, got: " + df.format(amount));
        }
        balance += amount;
        return "DEPOSITED: " + df.format(amount) + " *** " + checkBalance();
    }

    public String withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive, got: " + df.format(amount));
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient balance. Requested: " + df.format(amount)
                    + ", Available: " + df.format(balance));
        }
        balance -= amount;
        return "WITHDRAWN: " + df.format(amount) + " *** " + checkBalance();
    }
}
