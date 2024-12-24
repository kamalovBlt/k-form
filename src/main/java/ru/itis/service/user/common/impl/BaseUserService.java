package ru.itis.service.user.common.impl;

import ru.itis.model.User;
import ru.itis.repository.api.UserRepository;
import ru.itis.repository.impl.UserRepositoryImpl;
import ru.itis.exception.EntityNotFoundException;
import ru.itis.exception.IdNotReturnsException;
import ru.itis.service.user.common.api.UserService;
import ru.itis.exception.UserNotFoundException;

import java.util.UUID;

public class BaseUserService implements UserService {

    private final UserRepository userRepository;

    public BaseUserService() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );
    }

    @Override
    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", uuid)
        );
    }

    @Override
    public UUID save(User user) {
        return userRepository.save(user).orElseThrow(
                () -> new IdNotReturnsException("User")
        );
    }

    @Override
    public boolean updateByUserId(User user) {
        return userRepository.updateByUserId(user);
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return userRepository.deleteById(uuid);
    }
}
