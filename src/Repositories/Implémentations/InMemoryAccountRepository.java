package Repositories.Implementations;

import Models.Account;
import Models.User;
import Repositories.Interfaces.AccountRepository;

import java.util.*;

public class InMemoryAccountRepository implements AccountRepository {
    private static final Set<Account> accounts = new HashSet<>();
    private static InMemoryAccountRepository instance;

    private InMemoryAccountRepository() {
    }

    public static InMemoryAccountRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryAccountRepository();
        }
        return instance;
    }

    @Override
    public String save(Account account) {

        boolean exists = accounts.stream()
                .anyMatch(a -> a.getOwnerUserId().equals(account.getOwnerUserId())
                        && a.getType() == account.getType());

        if (exists) {
            return "This user already has a " + account.getType() + " account!";
        }

        accounts.add(account);
        return "Account " + account.getType() + " created successfully!";
    }

    @Override
    public Optional<Account> findById(String accountId) {
        return accounts.stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .findFirst();
    }

    @Override
    public List<Account> findByOwnerId(UUID ownerUserId) {
        return accounts.stream()
                .filter(a -> a.getOwnerUserId().equals(ownerUserId))
                .toList();
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    public Optional<Account> checkUserAccountType(User user, Account.AccountType accountType) {
        return findByOwnerId(user.getId()).stream()
                .filter(a -> a.getType() == accountType)
                .findFirst();

    }

    public void delete(Account account) {
        accounts.remove(account);
    }
}
