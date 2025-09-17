package Services;

import Models.Account;
import Models.User;
import Repositories.Implementations.InMemoryAccountRepository;

import Utils.ConsoleUtils;
import Utils.AmountUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static Models.Account.AccountType.*;

public class AccountService {

    private final InMemoryAccountRepository accountRepository=InMemoryAccountRepository.getInstance();


    // 1️⃣ Create a new account
    public void createAccount(String ownerEmail) {
        if(AuthService.authenticatedUser==null){
            System.out.println("User is not logged in");
            return;
        }
        User user=AuthService.authenticatedUser;
        System.out.println("Creating account for " + user.getEmail());
        System.out.println("Account Types:");
        System.out.println("1. Checking");
        System.out.println("2. Savings");
        System.out.println("3. Business");
        int choice =ConsoleUtils.readInt("Please chose the type of account you would like to create:",1,3);

        Account.AccountType accountType;
        switch (choice) {
            case 1 -> accountType = CHECKING;
            case 2 -> accountType = SAVINGS;
            case 3 -> accountType = BUSINESS;
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }
        Optional<Account> hasAccountType=accountRepository.checkUserAccountType(user,accountType);
        if(hasAccountType.isPresent()){
            System.out.println("Account already exists");
            return;
        }

        BigDecimal initialBalance = ConsoleUtils.readPositiveBigDecimal("Initial deposit amount: ");

        Account account = new Account(user.getId(), accountType);

        account.setBalance(initialBalance);

        accountRepository.save(account);
        System.out.println( accountType +" Account created successfully with balance: " + initialBalance);
    }

    // 2️⃣ List accounts for a user
    public void listAccounts() {
        if(AuthService.authenticatedUser==null){
            System.out.println("User is not logged in");
            return;
        }
        User user=AuthService.authenticatedUser;
        List<Account> accounts = accountRepository.findByOwnerId(user.getId());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }
        System.out.println("Accounts for " + user.getFullName() + ":");
        for (Account acc : accounts) {
            System.out.println("Account ID: " + acc.getAccountId()+" | Type: "+ acc.getType() + " | Balance: " + acc.getBalance());
        }
        return;
    }

    // 3️⃣ Deposit into an account
    public void deposit() {
        if(AuthService.authenticatedUser==null){
            System.out.println("User is not logged in");
            return;
        }
        User user=AuthService.authenticatedUser;
        List<Account> userAccounts=accountRepository.findByOwnerId(user.getId());

        if (userAccounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }
        System.out.println("Select Account for " + user.getFullName() + " Deposit :");
        for(int i=0;i<userAccounts.size();i++){
            Account acc=userAccounts.get(i);
            System.out.println((i+1) + ") Account ID: " + acc.getAccountId()
                    + " | Type: " + acc.getType() + " | Balance: " + acc.getBalance());

        }
        int choice = ConsoleUtils.readInt("Pick an account: ", 1, userAccounts.size());
        Account account = userAccounts.get(choice - 1);

        BigDecimal amount = ConsoleUtils.readPositiveBigDecimal("Deposit amount: ");

        account.deposit(amount);

        System.out.println("Deposit of " + amount + " successful. New balance: " + account.getBalance());

        return;
    }

    // 4️⃣ Withdraw from an account
    public void withdraw() {
        if(AuthService.authenticatedUser==null){
            System.out.println("User is not logged in");
            return;
        }
        User user=AuthService.authenticatedUser;
        List<Account> userAccounts=accountRepository.findByOwnerId(user.getId());

        if (userAccounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }
        System.out.println("Select Account for " + user.getFullName() + " Deposit :");
        for(int i=0;i<userAccounts.size();i++){
            Account acc=userAccounts.get(i);
            System.out.println((i+1) + ") Account ID: " + acc.getAccountId()
                    + " | Type: " + acc.getType() + " | Balance: " + acc.getBalance());

        }
        int choice = ConsoleUtils.readInt("Pick an account: ", 1, userAccounts.size());
        Account account = userAccounts.get(choice - 1);

        BigDecimal amount = ConsoleUtils.readPositiveBigDecimal("Withdrawal amount: ");

        if (AmountUtils.hasSufficientBalance(account.getBalance(), amount)) {
            account.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance!");
        }
        return;
    }

    // 5️⃣ Close an account
    public void closeAccount() {
        if(AuthService.authenticatedUser==null){
            System.out.println("User is not logged in");
            return;
        }
        User user=AuthService.authenticatedUser;
        List<Account> userAccounts=accountRepository.findByOwnerId(user.getId());

        if (userAccounts.isEmpty()) {
            System.out.println("No accounts found for this user.");
            return;
        }
        System.out.println("Select Account for " + user.getFullName() + " Deposit :");
        for(int i=0;i<userAccounts.size();i++){
            Account acc=userAccounts.get(i);
            System.out.println((i+1) + ") Account ID: " + acc.getAccountId()
                    + " | Type: " + acc.getType() + " | Balance: " + acc.getBalance());

        }
        int choice = ConsoleUtils.readInt("Pick an account: ", 1, userAccounts.size());
        Account account = userAccounts.get(choice - 1);


        if (!account.getBalance().equals(BigDecimal.ZERO)) {
            System.out.println("Account cannot be closed. Balance must be zero.");
            return;
        }
        accountRepository.delete(account);
        System.out.println("Account closed successfully.");
        return;
    }

    // 6️⃣ Find account by ID and check owner
    public Optional<Account> findAccountById(String accountId, String ownerEmail) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) return Optional.empty();
        Account acc = accountOpt.get();
        if (!acc.getOwnerEmail().equals(ownerEmail)) {
            System.out.println("You are not the owner of this account!");
            return Optional.empty();
        }
        return Optional.of(acc);
    }




}

