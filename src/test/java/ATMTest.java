import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM(1000.00);
    }

    @Nested
    @DisplayName("checkBalance")
    class CheckBalanceTests {

        @Test
        @DisplayName("returns formatted balance for initial amount")
        void returnsFormattedBalance() {
            String result = atm.checkBalance();
            assertEquals("YOUR BALANCE: ₹1000.00", result);
        }

        @Test
        @DisplayName("returns formatted balance with zero initial amount")
        void returnsZeroBalance() {
            ATM zeroAtm = new ATM(0.00);
            assertEquals("YOUR BALANCE: ₹0.00", zeroAtm.checkBalance());
        }

        @Test
        @DisplayName("returns formatted balance with decimal precision")
        void returnsDecimalBalance() {
            ATM decimalAtm = new ATM(123.456);
            assertEquals("YOUR BALANCE: ₹123.46", decimalAtm.checkBalance());
        }

        @Test
        @DisplayName("returns formatted balance for large amounts")
        void returnsLargeBalance() {
            ATM largeAtm = new ATM(999999.99);
            assertEquals("YOUR BALANCE: ₹999999.99", largeAtm.checkBalance());
        }
    }

    @Nested
    @DisplayName("deposit")
    class DepositTests {

        @Test
        @DisplayName("deposits valid amount and updates balance")
        void depositsValidAmount() {
            String result = atm.deposit(500.00);
            assertEquals("DEPOSITED: ₹500.00 *** YOUR BALANCE: ₹1500.00", result);
        }

        @Test
        @DisplayName("rejects zero deposit amount")
        void rejectsZeroDeposit() {
            String result = atm.deposit(0);
            assertEquals("INVALID DEPOSIT AMOUNT", result);
        }

        @Test
        @DisplayName("rejects negative deposit amount")
        void rejectsNegativeDeposit() {
            String result = atm.deposit(-100);
            assertEquals("INVALID DEPOSIT AMOUNT", result);
        }

        @Test
        @DisplayName("deposits small fractional amount")
        void depositsSmallFraction() {
            String result = atm.deposit(0.01);
            assertEquals("DEPOSITED: ₹0.01 *** YOUR BALANCE: ₹1000.01", result);
        }

        @Test
        @DisplayName("deposits large amount")
        void depositsLargeAmount() {
            String result = atm.deposit(50000.00);
            assertEquals("DEPOSITED: ₹50000.00 *** YOUR BALANCE: ₹51000.00", result);
        }

        @Test
        @DisplayName("multiple deposits accumulate correctly")
        void multipleDepositsAccumulate() {
            atm.deposit(200.00);
            atm.deposit(300.00);
            assertEquals("YOUR BALANCE: ₹1500.00", atm.checkBalance());
        }
    }

    @Nested
    @DisplayName("withdraw")
    class WithdrawTests {

        @Test
        @DisplayName("withdraws valid amount and updates balance")
        void withdrawsValidAmount() {
            String result = atm.withdraw(400.00);
            assertEquals("WITHDRAWN: ₹400.00 *** YOUR BALANCE: ₹600.00", result);
        }

        @Test
        @DisplayName("rejects zero withdrawal amount")
        void rejectsZeroWithdrawal() {
            String result = atm.withdraw(0);
            assertEquals("INVALID WITHDRAWAL AMOUNT", result);
        }

        @Test
        @DisplayName("rejects negative withdrawal amount")
        void rejectsNegativeWithdrawal() {
            String result = atm.withdraw(-50);
            assertEquals("INVALID WITHDRAWAL AMOUNT", result);
        }

        @Test
        @DisplayName("rejects withdrawal exceeding balance")
        void rejectsOverdraw() {
            String result = atm.withdraw(1500.00);
            assertEquals("INSUFFICIENT BALANCE", result);
        }

        @Test
        @DisplayName("allows withdrawal of exact balance")
        void withdrawsExactBalance() {
            String result = atm.withdraw(1000.00);
            assertEquals("WITHDRAWN: ₹1000.00 *** YOUR BALANCE: ₹0.00", result);
        }

        @Test
        @DisplayName("withdraws small fractional amount")
        void withdrawsSmallFraction() {
            String result = atm.withdraw(0.01);
            assertEquals("WITHDRAWN: ₹0.01 *** YOUR BALANCE: ₹999.99", result);
        }

        @Test
        @DisplayName("multiple withdrawals reduce balance correctly")
        void multipleWithdrawalsReduce() {
            atm.withdraw(200.00);
            atm.withdraw(300.00);
            assertEquals("YOUR BALANCE: ₹500.00", atm.checkBalance());
        }

        @Test
        @DisplayName("balance not affected after failed withdrawal")
        void balanceUnchangedAfterFailedWithdrawal() {
            atm.withdraw(2000.00);
            assertEquals("YOUR BALANCE: ₹1000.00", atm.checkBalance());
        }
    }

    @Nested
    @DisplayName("deposit and withdraw integration")
    class DepositWithdrawIntegrationTests {

        @Test
        @DisplayName("deposit then withdraw maintains correct balance")
        void depositThenWithdraw() {
            atm.deposit(500.00);
            atm.withdraw(700.00);
            assertEquals("YOUR BALANCE: ₹800.00", atm.checkBalance());
        }

        @Test
        @DisplayName("withdraw then deposit maintains correct balance")
        void withdrawThenDeposit() {
            atm.withdraw(600.00);
            atm.deposit(200.00);
            assertEquals("YOUR BALANCE: ₹600.00", atm.checkBalance());
        }

        @Test
        @DisplayName("sequence of operations with edge cases")
        void sequenceOfOperations() {
            atm.withdraw(1000.00);
            assertEquals("YOUR BALANCE: ₹0.00", atm.checkBalance());
            assertEquals("INSUFFICIENT BALANCE", atm.withdraw(1.00));
            atm.deposit(50.00);
            assertEquals("YOUR BALANCE: ₹50.00", atm.checkBalance());
        }
    }
}
