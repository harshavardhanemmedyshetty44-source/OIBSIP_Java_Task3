import java.text.DecimalFormat;

public class ATM {
    private double balance;
    private static final double MAX_TRANSACTION_AMOUNT = 1_000_000.00;
    public static final DecimalFormat df = new DecimalFormat("₹0.00");

    public ATM(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    public String checkBalance() {
        return "YOUR BALANCE: " + df.format(balance);
    }

    public String deposit(double amount) {
        if (amount <= 0) {
            return "INVALID DEPOSIT AMOUNT";
        }
        if (amount > MAX_TRANSACTION_AMOUNT) {
            return "DEPOSIT AMOUNT EXCEEDS MAXIMUM LIMIT OF " + df.format(MAX_TRANSACTION_AMOUNT);
        }
        if (balance + amount < balance) {
            return "DEPOSIT WOULD CAUSE BALANCE OVERFLOW";
        }
        balance += amount;
        return "DEPOSITED: " + df.format(amount) + " *** " + checkBalance();
    }

    public String withdraw(double amount) {
        if (amount <= 0) {
            return "INVALID WITHDRAWAL AMOUNT";
        }
        if (amount > MAX_TRANSACTION_AMOUNT) {
            return "WITHDRAWAL AMOUNT EXCEEDS MAXIMUM LIMIT OF " + df.format(MAX_TRANSACTION_AMOUNT);
        }
        if (amount > balance) {
            return "INSUFFICIENT BALANCE";
        }
        balance -= amount;
        return "WITHDRAWN: " + df.format(amount) + " *** " + checkBalance();
    }
}
