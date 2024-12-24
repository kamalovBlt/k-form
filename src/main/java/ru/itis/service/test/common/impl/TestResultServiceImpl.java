package ru.itis.service.test.common.impl;

import ru.itis.model.TestResult;
import ru.itis.repository.api.TestResultRepository;
import ru.itis.repository.impl.TestResultRepositoryImpl;
import ru.itis.exception.IdNotReturnsException;
import ru.itis.service.test.common.api.TestResultService;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepository;

    public TestResultServiceImpl() {
        this.testResultRepository = new TestResultRepositoryImpl();
    }

    @Override
    public List<TestResult> findByTestId(UUID uuid) {
        return testResultRepository.findByTestId(uuid);
    }

    @Override
    public UUID save(TestResult testResult, Connection connection) {
        return testResultRepository.save(testResult, connection).orElseThrow(
                () -> new IdNotReturnsException("TestResult")
        );
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return testResultRepository.deleteById(uuid);
    }
}
