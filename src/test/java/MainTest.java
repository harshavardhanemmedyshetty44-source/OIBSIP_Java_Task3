import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private String getOutput() {
        return outputStream.toString();
    }

    @Test
    @DisplayName("blocks card after 3 invalid PIN attempts")
    void blocksCardAfterThreeInvalidAttempts() {
        provideInput("0000\n0000\n0000\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("INVALID PIN"));
        assertTrue(output.contains("YOUR CARD BLOCKED! TOO MANY ATTEMPTS"));
    }

    @Test
    @DisplayName("successful login with correct PIN")
    void successfulLogin() {
        provideInput("1984\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("LOGIN SUCCESSFULL! WELCOME TO ATM"));
        assertTrue(output.contains("THANK YOU FOR USING THE ATM!"));
    }

    @Test
    @DisplayName("login after one failed attempt")
    void loginAfterOneFailedAttempt() {
        provideInput("0000\n1984\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("INVALID PIN"));
        assertTrue(output.contains("2 ATTEMPTS LEFT"));
        assertTrue(output.contains("LOGIN SUCCESSFULL! WELCOME TO ATM"));
    }

    @Test
    @DisplayName("login after two failed attempts")
    void loginAfterTwoFailedAttempts() {
        provideInput("0000\n1111\n1984\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("2 ATTEMPTS LEFT"));
        assertTrue(output.contains("1 ATTEMPTS LEFT"));
        assertTrue(output.contains("LOGIN SUCCESSFULL! WELCOME TO ATM"));
    }

    @Test
    @DisplayName("check balance shows initial balance")
    void checkBalanceOption() {
        provideInput("1984\n1\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("YOUR BALANCE: ₹500.00"));
    }

    @Test
    @DisplayName("deposit money updates balance")
    void depositMoneyOption() {
        provideInput("1984\n2\n200.00\n1\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("DEPOSITED: ₹200.00"));
        assertTrue(output.contains("YOUR BALANCE: ₹700.00"));
    }

    @Test
    @DisplayName("withdraw money updates balance")
    void withdrawMoneyOption() {
        provideInput("1984\n3\n100.00\n1\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("WITHDRAWN: ₹100.00"));
        assertTrue(output.contains("YOUR BALANCE: ₹400.00"));
    }

    @Test
    @DisplayName("withdraw with insufficient balance shows error")
    void withdrawInsufficientBalance() {
        provideInput("1984\n3\n1000.00\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("INSUFFICIENT BALANCE"));
    }

    @Test
    @DisplayName("invalid menu choice shows error message")
    void invalidMenuChoice() {
        provideInput("1984\n9\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("INVALID CHOICE! TRY AGAIN"));
    }

    @Test
    @DisplayName("full workflow: deposit, withdraw, check balance, exit")
    void fullWorkflow() {
        provideInput("1984\n2\n300.00\n3\n150.00\n1\n4\n");
        Main.main(new String[]{});
        String output = getOutput();
        assertTrue(output.contains("DEPOSITED: ₹300.00"));
        assertTrue(output.contains("WITHDRAWN: ₹150.00"));
        assertTrue(output.contains("YOUR BALANCE: ₹650.00"));
        assertTrue(output.contains("THANK YOU FOR USING THE ATM!"));
    }
}
