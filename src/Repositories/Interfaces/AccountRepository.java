package Repositories.Interfaces;

import Models.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    String save(Account account);
    Optional<Account> findById(String accountId);
    List<Account> findByOwnerId(UUID ownerUserId);
    List<Account> findAll();
}