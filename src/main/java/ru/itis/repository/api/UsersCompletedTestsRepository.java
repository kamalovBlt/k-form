package ru.itis.repository.api;

import ru.itis.model.Result;
import ru.itis.model.stats.CompletedTestIdStats;
import ru.itis.model.stats.UserIdResultCompletedAt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersCompletedTestsRepository {

    void addRelation(UUID userId, UUID testId, LocalDateTime completedAt, int result);

    List<UUID> getTestIds(UUID userId);
    List<UUID> getUsersIds(UUID testId);

    boolean removeRelation(UUID userId, UUID testId);

    Optional<Result> getTestResult(UUID userId, UUID testId);

    List<UserIdResultCompletedAt> getUsersScoreCompletedAtByTestId(UUID testId);
    List<CompletedTestIdStats> getUsersScoreCompletedAtByUserId(UUID userId);

}
