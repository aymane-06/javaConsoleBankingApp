package Services;

import Models.Account;
import Models.Transaction;
import Repositories.Implementations.InMemoryAccountRepository;
import Repositories.Implementations.InMemoryTransactionRepository;
import Utils.AmountUtils;
import Utils.ConsoleUtils;


import java.math.BigDecimal;
import java.util.List;

import static Models.Transaction.TransactionType.DEPOSIT;


public class TransactionService {
    private static final InMemoryAccountRepository accountRepository=InMemoryAccountRepository.getInstance();
    private static final InMemoryTransactionRepository transactionRepository=InMemoryTransactionRepository.getInstance();

    public static void deposit() {
        Account account = AccountService.getAccount();
        if (account == null) {
            return;
        }
        BigDecimal amount = ConsoleUtils.readPositiveBigDecimal("Deposit amount: ");
        String description = ConsoleUtils.readString("Description: ");
        account.deposit(amount);
        Transaction transaction=new Transaction(account.getAccountId(), DEPOSIT,amount, account.getAccountId(),description );
        transactionRepository.save(transaction);
        System.out.println("Deposit of " + amount + " successful. New balance: " + account.getBalance());

        return;
    }

    // 4️⃣ Withdraw from an account
    public static void withdraw() {
        Account account = AccountService.getAccount();
        if (account == null) {
            return;
        }

        BigDecimal amount = ConsoleUtils.readPositiveBigDecimal("Withdrawal amount: ");

        if (AmountUtils.hasSufficientBalance(account.getBalance(), amount)) {
            account.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance!");
            return;
        }
        String description = ConsoleUtils.readString("Description: ");
        Transaction transaction=new Transaction(account.getAccountId(), Transaction.TransactionType.WITHDRAW,amount, account.getAccountId(),description );
        transactionRepository.save(transaction);
        return;
    }

    public static void Transfer(boolean isInner) {
        Account account1 = AccountService.getAccount();
        if (account1 == null) {
            return;
        }

        Account account2 = null;
        String outerAccountRIB = null;

        if (isInner) {
            account2 = AccountService.getAccount();
            if (account1 == account2) {
                System.out.println("Cannot transfer to the same account");
                return;
            }
        } else {
            outerAccountRIB = ConsoleUtils.readString("Enter the RIB of the Account : ");
        }

        BigDecimal amount = ConsoleUtils.readPositiveBigDecimal("Transfer amount: ");
        if (!AmountUtils.hasSufficientBalance(account1.getBalance(), amount)) {
            System.out.println("Insufficient balance!");
            return;
        }
        String description = ConsoleUtils.readString("Description: ");

        // Perform transfer
        if (isInner) {
            account1.transfer(amount, account2);
            System.out.println("Transfer successful. New balances: " + account1.getBalance() + " | " + account2.getBalance());
        } else {
            account1.transfer(amount, outerAccountRIB); // assuming you support this overload
            System.out.println("Transfer successful. New balance: " + account1.getBalance());
        }

        // Create transactions
        Transaction transaction1;
        Transaction transaction2 = null;

        if (isInner) {
            transaction1 = new Transaction(
                    account1.getAccountId(),
                    Transaction.TransactionType.TRANSFEROUT,
                    amount,
                    account2.getAccountId(),
                    description
            );

            transaction2 = new Transaction(
                    account2.getAccountId(),
                    Transaction.TransactionType.TRANSFERIN,
                    amount,
                    account1.getAccountId(),
                    description
            );
        } else {
            transaction1 = new Transaction(
                    account1.getAccountId(),
                    Transaction.TransactionType.TRANSFEROUT,
                    amount,
                    outerAccountRIB,
                    description
            );
        }

        transactionRepository.save(transaction1);
        if (isInner) {
            transactionRepository.save(transaction2);
        }
    }


    public static void listTransactions(){
        Account account = AccountService.getAccount();
        if (account == null) {
            return;
        }
        else{
            System.out.println("Transactions for account "+account.getAccountId()+"|"+account.getType());
            System.out.println("---------------------------------------");
            List<Transaction> transactions=transactionRepository.findByAccountId(account.getAccountId());
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }

}
