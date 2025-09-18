package Services;

import Models.User;

import Repositories.Implementations.InMemoryUserRepository;
import Utils.ConsoleUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    public static User authenticatedUser;

    private static final InMemoryUserRepository userRepository = InMemoryUserRepository.getInstance();



    public static String register() {
        String fullName = ConsoleUtils.readString("Full Name: ");
        String email;
        while (true) {
            email = ConsoleUtils.readString("Email: ");
            String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if (email.matches(emailRegex) ) break;
            System.out.println("Invalid email please try again");
        }
        String address = ConsoleUtils.readString("Address: ");
        String password;
        while (true) {
            password = ConsoleUtils.readString("Password (min 6 chars): ");
            if (password.length() >= 6) break;
            System.out.println("Password must be at least 6 chars.");
        }

        User user = new User(fullName, email, address, password);

        userRepository.save(user);

       return login(user.getEmail(),password);


    }
    public static String login(){
        return login(null,null);
    }


    public static String login(String email, String password) {
        if (email == null) {
            email = ConsoleUtils.readString("Email: ");
        }
        if (password == null) {
            password = ConsoleUtils.readString("Password: ");
        }
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return "User not found!";
        User userObj = user.get();
        if (BCrypt.checkpw(password, userObj.getPasswordHash())) {
            authenticatedUser = userObj;
            return "Login successful! Welcome " + userObj.getFullName();
        } else return "Invalid password.";
    }

    public static void logout() {
        authenticatedUser = null;
    }
    
    public static void changePassword(){
        User user = authenticatedUser;
        if (user == null) {
            System.out.println("You are not logged in.");
        }
        String newPassword;
        while (true) {
            newPassword = ConsoleUtils.readString("New Password (min 6 chars): ");
            if (newPassword.length() >= 6) break;
            System.out.println("Password must be at least 6 chars.");
        }
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        InMemoryUserRepository.update(user,hashedPassword);

         System.out.println("Password changed successfully.");
    }



}
