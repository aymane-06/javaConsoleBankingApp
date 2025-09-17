package Repositories.Implémentations;

import Models.User;
import Repositories.Interfaces.UserRepository;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private static final Set<User> users = new HashSet<>();
    private static InMemoryUserRepository instance;

    private InMemoryUserRepository() {}

    public static InMemoryUserRepository getInstance() {
        if(instance == null) {
            instance = new InMemoryUserRepository();
        }
        return instance;
    }
    @Override
    public  String save(User user) {
       Optional<User> registred = findByEmail(user.getEmail());
       if(registred.isEmpty()) {
           users.add(user);
           return "User "+user.getFullName()+" Registered successfully!";
       }
       else {
           return "This User Email already exists! ";
       }

    }

    @Override
    public  Optional<User> findById(UUID id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public  Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public  List<User> findAll() {
        return new ArrayList<>(users);
    }
}

