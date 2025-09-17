package Models;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

public class Account {
    private final String accountId;
    private final UUID ownerUserId;
    private BigDecimal balance;
    private final Instant createdAt;
    private boolean active;
    private AccountType accountType;

    public enum AccountType {
        CHECKING,
        SAVINGS,
        BUSINESS
    }


    public Account(UUID ownerUserId, AccountType type) {
        this.accountId = generateAccountId();
        this.ownerUserId = ownerUserId;
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.createdAt = Instant.now();
        this.active = true;
        this.accountType = type;
    }

    private String generateAccountId() {
        String random = String.valueOf((int)(Math.random()*10000));
        return "BK-" + random + "-" + UUID.randomUUID().toString().substring(0, 4);
    }

    // Getters & Setters
    public String getAccountId() {
        return accountId;
    }

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public AccountType getType() {
        return accountType;
    }
    public void setType(AccountType type) {
        this.accountType = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", ownerUserId=" + ownerUserId +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", active=" + active +
                ", type=" + accountType +
                '}';
    }

}

