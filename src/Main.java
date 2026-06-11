import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils console = new ConsoleUtils(new Scanner(System.in));
        AuthService auth = new AuthService(1984, 3);

        if (!auth.authenticate(console)) {
            console.close();
            return;
        }

        System.out.println("LOGIN SUCCESSFULL! WELCOME TO ATM");
        ATM atm = new ATM(500.00);
        boolean exit = false;

        while (!exit) {
            console.displayMenu("CHOOSE AN OPTION",
                    "CHECK BALANCE", "DEPOSIT MONEY", "WITHDRAW MONEY", "EXIT");
            int choice = console.promptInt("ENTER YOUR CHOICE: ");

            switch (choice) {
                case 1:
                    System.out.println(atm.checkBalance());
                    break;
                case 2:
                    double dep = console.promptDouble("ENTER DEPOSIT MONEY: ");
                    System.out.println(atm.deposit(dep));
                    break;
                case 3:
                    double wd = console.promptDouble("ENTER WITHDRAWL MONEY: ");
                    System.out.println(atm.withdraw(wd));
                    break;
                case 4:
                    exit = true;
                    System.out.println("THANK YOU FOR USING THE ATM! ");
                    break;
                default:
                    System.out.println("INVALID CHOICE! TRY AGAIN");
            }
        }
        console.close();
    }
}
