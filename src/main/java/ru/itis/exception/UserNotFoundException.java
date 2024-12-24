package ru.itis.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User not found: %s".formatted(email));
    }

}
