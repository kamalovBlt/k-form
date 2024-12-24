package ru.itis.repository.api;

import ru.itis.model.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestRepository {

    List<Test> findAll();

    List<Test> findByCategory(String category);

    Optional<Test> findById(UUID uuid);

    Optional<UUID> save(Test test);

    boolean deleteById(UUID uuid);

    int countOfCreatedTestsByUser(UUID userId);

    List<UUID> findCreatedTestsByUser(UUID userId);

}
