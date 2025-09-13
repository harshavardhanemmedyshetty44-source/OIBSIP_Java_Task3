import java.util.*;
public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        final int pin=1984;
        int attempts=0;
        boolean authorized=false;
        while(attempts<3){
            System.out.print("ENTER YOUR 4 DIGIT PIN: " );
            int input=sc.nextInt();
            if(input==pin){
                authorized=true;
                break;
            }
            else{
                attempts++;
                System.out.println("INVALID PIN "+(3-attempts)+" ATTEMPTS LEFT");
            }
        }
        if(!authorized){
            System.out.println("YOUR CARD BLOCKED! TOO MANY ATTEMPTS");
            sc.close();
            return;
        }

        boolean exit=false;
        ATM atm=new ATM(1000.00);
        System.out.println("LOGIN SUCCESSFULL! WELCOME TO ATM");
        while(!exit){
            System.out.println("CHOOSE AN OPTION");
            System.out.println("1.CHECK BALANCE");
            System.out.println("2.DEPOSIT MONEY");
            System.out.println("3.WITHDRAW MONEY");
            System.out.println("4.EXIT");
            System.out.print("ENTER YOUR CHOICE: ");
            int choice=sc.nextInt();
            switch(choice){
                case 1:
                    System.out.println(atm.checkBalance());
                    break;

                case 2:
                    System.out.print("ENTER DEPOSIT MONEY: ");
                    double dep=sc.nextDouble();
                    System.out.println(atm.deposit(dep));
                    break;

                case 3:
                    System.out.print("ENTER WITHDRAWL MONEY: ");
                    double wd=sc.nextDouble();
                    System.out.println(atm.withdraw(wd));
                    break;

                case 4:
                    exit=true;
                    System.out.println("THANK YOU FOR USING THE ATM! ");
                    break;

                default:
                    System.out.println("INVALID CHOICE! TRY AGAIN");
            }




        }
        sc.close();

    }
}
