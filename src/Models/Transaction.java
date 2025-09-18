package Models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class Transaction {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFERIN,
        TRANSFEROUT
    }

    private final UUID id;
    private final Instant timestamp;
    private final String accountId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final String counterpartyAccountId;
    private final String description;

    public Transaction(String accountId, TransactionType type, BigDecimal amount, String counterpartyAccountId, String description) {
        this.id = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.counterpartyAccountId = counterpartyAccountId;
        this.description = description;
    }

    // Getters
    public UUID getId() { return id; }

    public Instant getTimestamp() { return timestamp; }

    public String getAccountId() { return accountId; }

    public TransactionType getType() { return type; }

    public BigDecimal getAmount() { return amount; }

    public String getCounterpartyAccountId() { return counterpartyAccountId; }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Transaction : " +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", accountId='" + accountId + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", counterpartyAccountId='" + counterpartyAccountId + '\'' +
                ", description='" + description + '\'';
    }
}

