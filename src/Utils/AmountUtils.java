package Utils;

import java.math.BigDecimal;

public class AmountUtils {
    public static boolean isValidAmount(BigDecimal amount) {
        // true if amount > 0
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean hasSufficientBalance(BigDecimal balance, BigDecimal withdrawal) {
        // true if balance >= withdrawal
        return balance != null && withdrawal != null && balance.compareTo(withdrawal) >= 0;
    }

}
