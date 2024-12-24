package ru.itis.exception;

public class IdNotReturnsException extends RuntimeException {
    public IdNotReturnsException(String entityName) {
        super("ID not returns %s".formatted(entityName));
    }
}
