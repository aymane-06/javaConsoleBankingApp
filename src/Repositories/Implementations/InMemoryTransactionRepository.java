package Repositories.Implementations;

import Models.Transaction;
import Repositories.Interfaces.TransactionRepository;

import java.util.*;

public class InMemoryTransactionRepository implements TransactionRepository {
    private static final Set<Transaction> transactions = new HashSet<>();
    private static InMemoryTransactionRepository instance;

    private InMemoryTransactionRepository() {}

    public static InMemoryTransactionRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryTransactionRepository();
        }
        return instance;
    }

    @Override
    public String save(Transaction transaction) {
        transactions.add(transaction);
        return "Transaction " + transaction.getId() + " recorded successfully!";
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return transactions.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        return transactions.stream()
                .filter(t -> t.getAccountId().equals(accountId))
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .toList();
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }
}
