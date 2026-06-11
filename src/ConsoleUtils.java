import java.util.Scanner;

/**
 * Shared utility for console I/O operations.
 * Eliminates duplicated prompt-then-read patterns throughout the application.
 */
public class ConsoleUtils {
    private final Scanner scanner;

    public ConsoleUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    public int promptInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public double promptDouble(String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }

    public void displayMenu(String title, String... options) {
        System.out.println(title);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + "." + options[i]);
        }
    }

    public void close() {
        scanner.close();
    }
}
