package Utils;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    // Read a non-empty string
    public static String readString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                break;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
        return input;
    }

    // Read a positive double (for amounts)
    public static BigDecimal readPositiveBigDecimal(String prompt) {
        BigDecimal value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = new  BigDecimal(input);
                if (value.compareTo(BigDecimal.ZERO) > 0) break;
                else System.out.println("Amount must be positive. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
        return value;
    }

    // Read an integer within a range
    public static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = Integer.parseInt(input);
                if (value >= min && value <= max) break;
                else System.out.println("Enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
        return value;
    }
}
