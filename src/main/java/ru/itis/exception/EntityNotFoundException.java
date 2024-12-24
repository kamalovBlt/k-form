package ru.itis.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, UUID entityId) {
        super("Entity %s with id %s not found".formatted(entityName, entityId));
    }
}
