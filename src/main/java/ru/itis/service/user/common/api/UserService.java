package ru.itis.service.user.common.api;

import ru.itis.model.User;

import java.util.UUID;

public interface UserService {

    User findByEmail(String email);

    User findById(UUID uuid);

    UUID save(User user);

    boolean updateByUserId(User user);

    boolean deleteById(UUID uuid);

}
