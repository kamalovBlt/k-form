package ru.itis.service.test.common.api;

import ru.itis.model.TestResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestResultService {

    List<TestResult> findByTestId(UUID uuid);

    UUID save(TestResult testResult);

    boolean deleteById(UUID uuid);

}