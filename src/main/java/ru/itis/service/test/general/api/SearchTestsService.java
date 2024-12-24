package ru.itis.service.test.general.api;

import ru.itis.model.Test;

import java.util.List;
import java.util.UUID;

public interface SearchTestsService {
    List<Test> getTestForRecommendations(UUID userId);
    List<Test> getTestOfUserRequest(String request);
}
