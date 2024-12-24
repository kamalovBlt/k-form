package ru.itis.service.test.common.impl;

import ru.itis.model.Test;
import ru.itis.repository.api.TestRepository;
import ru.itis.repository.impl.TestRepositoryImpl;
import ru.itis.exception.EntityNotFoundException;
import ru.itis.exception.IdNotReturnsException;
import ru.itis.service.test.common.api.TestService;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl() {
        this.testRepository = new TestRepositoryImpl();
    }

    @Override
    public Test findById(UUID uuid) {
        return testRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Test", uuid)
        );
    }

    @Override
    public UUID save(Test test, Connection connection) {
        return testRepository.save(test, connection).orElseThrow(
                () -> new IdNotReturnsException("Test")
        );
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return testRepository.deleteById(uuid);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> findByCategory(String category) {
        return testRepository.findByCategory(category);
    }

    @Override
    public int countOfCreatedTestsByUser(UUID userId) {
        return testRepository.countOfCreatedTestsByUser(userId);
    }

    @Override
    public List<UUID> findCreatedTestsByUser(UUID userId) {
        return testRepository.findCreatedTestsByUser(userId);
    }
}
