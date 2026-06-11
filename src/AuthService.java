/**
 * Handles PIN-based authentication with retry logic.
 * Extracted from Main to separate concerns and make auth reusable.
 */
public class AuthService {
    private final int pin;
    private final int maxAttempts;

    public AuthService(int pin, int maxAttempts) {
        this.pin = pin;
        this.maxAttempts = maxAttempts;
    }

    public boolean authenticate(ConsoleUtils console) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            int input = console.promptInt("ENTER YOUR 4 DIGIT PIN: ");
            if (input == pin) {
                return true;
            }
            attempts++;
            System.out.println("INVALID PIN " + (maxAttempts - attempts) + " ATTEMPTS LEFT");
        }
        System.out.println("YOUR CARD BLOCKED! TOO MANY ATTEMPTS");
        return false;
    }
}
