package ru.itis.service.test.common.api;

import ru.itis.model.Result;
import ru.itis.model.stats.CompletedTestIdStats;
import ru.itis.model.stats.CompletedTestStats;
import ru.itis.model.stats.UserResultCompletedAt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserCompletedTestsService {

    void addRelation(UUID userId, UUID testId, LocalDateTime completedAt, int result);

    List<UUID> getTestIds(UUID userId);
    List<UUID> getUsersIds(UUID testId);

    boolean removeRelation(UUID userId, UUID testId);

    Result getTestResult(UUID userId, UUID testId);

    List<UserResultCompletedAt> getUsersScoreCompletedAtByTestId(UUID testId);
    List<CompletedTestStats> getUsersScoreCompletedAtByUserId(UUID userId);

}
