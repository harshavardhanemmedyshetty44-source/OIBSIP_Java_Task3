import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    // PIN stored as SHA-256 hash instead of plaintext.
    // Original plaintext PIN is no longer in source code.
    private static final String PIN_HASH = "4dea5c7cb70f50322ec9d734aa4aa078be9227c05251e18991c596f387552370";
    private static final int MAX_ATTEMPTS = 3;
    private static final DateTimeFormatter LOG_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        boolean authorized = false;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("ENTER YOUR 4 DIGIT PIN: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("PIN CANNOT BE EMPTY");
                continue;
            }

            if (!input.matches("\\d{4}")) {
                System.out.println("PIN MUST BE EXACTLY 4 DIGITS");
                attempts++;
                auditLog("AUTH", "Invalid PIN format - " + (MAX_ATTEMPTS - attempts) + " attempts left");
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println((MAX_ATTEMPTS - attempts) + " ATTEMPTS LEFT");
                }
                continue;
            }

            if (verifyPin(input)) {
                authorized = true;
                auditLog("AUTH", "Login successful");
                break;
            } else {
                attempts++;
                auditLog("AUTH", "Incorrect PIN - " + (MAX_ATTEMPTS - attempts) + " attempts left");
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("INVALID PIN " + (MAX_ATTEMPTS - attempts) + " ATTEMPTS LEFT");
                }
            }
        }

        if (!authorized) {
            auditLog("AUTH", "Account locked - too many failed attempts");
            System.out.println("YOUR CARD BLOCKED! TOO MANY ATTEMPTS");
            sc.close();
            return;
        }

        boolean exit = false;
        ATM atm = new ATM(500.00);
        System.out.println("LOGIN SUCCESSFUL! WELCOME TO ATM");

        while (!exit) {
            System.out.println("CHOOSE AN OPTION");
            System.out.println("1.CHECK BALANCE");
            System.out.println("2.DEPOSIT MONEY");
            System.out.println("3.WITHDRAW MONEY");
            System.out.println("4.EXIT");
            System.out.print("ENTER YOUR CHOICE: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT! PLEASE ENTER A NUMBER (1-4)");
                continue;
            }

            switch (choice) {
                case 1:
                    String balanceResult = atm.checkBalance();
                    System.out.println(balanceResult);
                    auditLog("TXN", "Balance inquiry");
                    break;

                case 2:
                    System.out.print("ENTER DEPOSIT MONEY: ");
                    double dep;
                    try {
                        dep = Double.parseDouble(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("INVALID INPUT! PLEASE ENTER A VALID AMOUNT");
                        continue;
                    }
                    String depResult = atm.deposit(dep);
                    System.out.println(depResult);
                    auditLog("TXN", "Deposit: " + dep + " -> " + depResult);
                    break;

                case 3:
                    System.out.print("ENTER WITHDRAWAL MONEY: ");
                    double wd;
                    try {
                        wd = Double.parseDouble(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("INVALID INPUT! PLEASE ENTER A VALID AMOUNT");
                        continue;
                    }
                    String wdResult = atm.withdraw(wd);
                    System.out.println(wdResult);
                    auditLog("TXN", "Withdrawal: " + wd + " -> " + wdResult);
                    break;

                case 4:
                    exit = true;
                    auditLog("SESSION", "User exited ATM");
                    System.out.println("THANK YOU FOR USING THE ATM!");
                    break;

                default:
                    System.out.println("INVALID CHOICE! TRY AGAIN");
            }
        }
        sc.close();
    }

    private static boolean verifyPin(String input) {
        String inputHash = sha256(input);
        if (inputHash == null) {
            return false;
        }
        // Constant-time comparison to prevent timing attacks
        return MessageDigest.isEqual(
                inputHash.getBytes(StandardCharsets.UTF_8),
                PIN_HASH.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("CRITICAL ERROR: SHA-256 not available");
            return null;
        }
    }

    private static void auditLog(String category, String message) {
        String timestamp = LocalDateTime.now().format(LOG_FMT);
        System.out.println("[AUDIT " + timestamp + "] [" + category + "] " + message);
    }
}
