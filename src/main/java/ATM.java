import java.text.DecimalFormat;
public class ATM {
    private double balance;
    public static final DecimalFormat df=new DecimalFormat("₹0.00");
    public ATM(double initialBalance){
        this.balance=initialBalance;
    }
    public String checkBalance(){
        return "YOUR BALANCE: "+df.format(balance);
    }
    public String deposit(double amount){
        if(amount<=0){
            return "INVALID DEPOSIT AMOUNT";
        }
        balance+=amount;
        return "DEPOSITED: "+df.format(amount)+" *** "+checkBalance();

    }
    public String withdraw(double amount){
        if(amount<=0){
            return "INVALID WITHDRAWAL AMOUNT";
        }
        if(amount>balance){
            return "INSUFFICIENT BALANCE";
        }
        balance-=amount;
        return "WITHDRAWN: "+df.format(amount)+" *** "+checkBalance();
    }
}
