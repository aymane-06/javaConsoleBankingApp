import Services.AccountService;
import Services.AuthService;
import Services.TransactionService;
import Utils.ConsoleUtils;

public class Main {
    private static final AccountService accountService = new AccountService();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("    Welcome to Java Banking System!");
        System.out.println("=================================================");

        boolean running = true;
        while (running) {
            if (AuthService.authenticatedUser == null) {
                running = showLoginMenu();
            } else {
                running = showMainMenu();
            }
        }

        System.out.println("Thank you for using Java Banking System. Goodbye!");
    }

    private static boolean showLoginMenu() {
        System.out.println("\n=== LOGIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        int choice = ConsoleUtils.readInt("Please select an option: ", 1, 3);

        switch (choice) {
            case 1 -> {
                String result = AuthService.login();
                System.out.println(result);
            }
            case 2 -> {
                String result = AuthService.register();
                System.out.println(result);
            }
            case 3 -> {
                return false;
            }
        }
        return true;
    }

    private static boolean showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Welcome, " + AuthService.authenticatedUser.getFullName() + "!");
        System.out.println("1. Account Management");
        System.out.println("2. Transaction Operations");
        System.out.println("3. View Transaction History");
        System.out.println("4. Logout");
        System.out.println("5. Exit");

        int choice = ConsoleUtils.readInt("Please select an option: ", 1, 5);

        switch (choice) {
            case 1 -> showAccountMenu();
            case 2 -> showTransactionMenu();
            case 3 -> TransactionService.listTransactions();
            case 4 -> {
                AuthService.logout();
                System.out.println("Successfully logged out.");
            }
            case 5 -> {
                return false;
            }
        }
        return true;
    }

    private static void showAccountMenu() {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== ACCOUNT MANAGEMENT ===");
            System.out.println("1. Create New Account");
            System.out.println("2. View My Accounts");
            System.out.println("3. Close Account");
            System.out.println("4. Change Password");
            System.out.println("5. Back to Main Menu");

            int choice = ConsoleUtils.readInt("Please select an option: ", 1, 5);

            switch (choice) {
                case 1 -> accountService.createAccount(AuthService.authenticatedUser.getEmail());
                case 2 -> accountService.listAccounts();
                case 3 -> accountService.closeAccount();
                case 4 -> AuthService.changePassword();
                case 5 -> backToMain = true;
            }
        }
    }

    private static void showTransactionMenu() {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n=== TRANSACTION OPERATIONS ===");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Back to Main Menu");

            int choice = ConsoleUtils.readInt("Please select an option: ", 1, 4);

            switch (choice) {
                case 1 -> TransactionService.deposit();
                case 2 -> TransactionService.withdraw();
                case 3 -> transferMenu();
                case 4 -> backToMain = true;
            }
        }
    }

    private static void transferMenu(){
        boolean back = false;

        while (!back) {
            System.out.println("\n=== TRANSFER OPERATIONS ===");
            System.out.println("1. Inner Transfer");
            System.out.println("2. Outer Transfer");
            System.out.println("3. Back");

            int choice = ConsoleUtils.readInt("Please select an option: ", 1, 3);

            switch (choice) {
                case 1 -> TransactionService.Transfer(true);
                case 2 -> TransactionService.Transfer(false);
                case 3 -> back = true;


            }
        }
    }

}

