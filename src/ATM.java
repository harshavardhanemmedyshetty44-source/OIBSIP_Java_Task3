import java.text.DecimalFormat;

public class ATM {
    private double balance;
    public static final DecimalFormat df = new DecimalFormat("₹0.00");

    public ATM(double initialBalance) {
        this.balance = initialBalance;
    }

    public String checkBalance() {
        return "YOUR BALANCE: " + df.format(balance);
    }

    public String deposit(double amount) {
        String error = validateAmount(amount, "DEPOSIT");
        if (error != null) {
            return error;
        }
        balance += amount;
        return formatTransactionResult("DEPOSITED", amount);
    }

    public String withdraw(double amount) {
        String error = validateAmount(amount, "WITHDRAWAL");
        if (error != null) {
            return error;
        }
        if (amount > balance) {
            return "INSUFFICIENT BALANCE";
        }
        balance -= amount;
        return formatTransactionResult("WITHDRAWN", amount);
    }

    private String validateAmount(double amount, String transactionType) {
        if (amount <= 0) {
            return "INVALID " + transactionType + " AMOUNT";
        }
        return null;
    }

    private String formatTransactionResult(String action, double amount) {
        return action + ": " + df.format(amount) + " *** " + checkBalance();
    }
}
