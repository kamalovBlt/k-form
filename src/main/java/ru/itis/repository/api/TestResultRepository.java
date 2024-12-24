package ru.itis.repository.api;

import ru.itis.model.Test;
import ru.itis.model.TestResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestResultRepository {

    List<TestResult> findByTestId(UUID uuid);

    Optional<UUID> save(TestResult testResult);

    boolean deleteById(UUID uuid);

}
