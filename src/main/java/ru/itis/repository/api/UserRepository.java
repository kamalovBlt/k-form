package ru.itis.repository.api;

import ru.itis.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID uuid);

    Optional<UUID> save(User user);

    boolean updateByUserId(User user);

    boolean deleteById(UUID uuid);

}
