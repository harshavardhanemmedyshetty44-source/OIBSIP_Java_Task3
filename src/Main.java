import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            final int pin = 1984;
            int attempts = 0;
            boolean authorized = false;

            while (attempts < 3) {
                System.out.print("ENTER YOUR 4 DIGIT PIN: ");
                int input;
                try {
                    input = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    attempts++;
                    System.out.println("PIN MUST BE A NUMBER. " + (3 - attempts) + " ATTEMPTS LEFT");
                    continue;
                }
                if (input == pin) {
                    authorized = true;
                    break;
                } else {
                    attempts++;
                    System.out.println("INVALID PIN. " + (3 - attempts) + " ATTEMPTS LEFT");
                }
            }

            if (!authorized) {
                System.out.println("YOUR CARD BLOCKED! TOO MANY ATTEMPTS");
                return;
            }

            ATM atm = new ATM(500.00);
            System.out.println("LOGIN SUCCESSFUL! WELCOME TO ATM");

            boolean exit = false;
            while (!exit) {
                System.out.println("CHOOSE AN OPTION");
                System.out.println("1.CHECK BALANCE");
                System.out.println("2.DEPOSIT MONEY");
                System.out.println("3.WITHDRAW MONEY");
                System.out.println("4.EXIT");
                System.out.print("ENTER YOUR CHOICE: ");

                int choice;
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("INVALID INPUT! PLEASE ENTER A NUMBER.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println(atm.checkBalance());
                        break;

                    case 2:
                        System.out.print("ENTER DEPOSIT MONEY: ");
                        try {
                            double dep = sc.nextDouble();
                            System.out.println(atm.deposit(dep));
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("INVALID INPUT! PLEASE ENTER A VALID AMOUNT.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.print("ENTER WITHDRAWAL MONEY: ");
                        try {
                            double wd = sc.nextDouble();
                            System.out.println(atm.withdraw(wd));
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("INVALID INPUT! PLEASE ENTER A VALID AMOUNT.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        exit = true;
                        System.out.println("THANK YOU FOR USING THE ATM!");
                        break;

                    default:
                        System.out.println("INVALID CHOICE! TRY AGAIN");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("INPUT STREAM CLOSED UNEXPECTEDLY. EXITING.");
        } catch (Exception e) {
            System.out.println("AN UNEXPECTED ERROR OCCURRED: " + e.getMessage());
        }
    }
}
