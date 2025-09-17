package Repositories.Interfaces;

import Models.User;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserRepository {
    String  save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
