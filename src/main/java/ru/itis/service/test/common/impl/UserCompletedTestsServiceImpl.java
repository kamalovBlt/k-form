package ru.itis.service.test.common.impl;

import ru.itis.model.Result;
import ru.itis.model.stats.CompletedTestIdStats;
import ru.itis.model.stats.CompletedTestStats;
import ru.itis.model.stats.UserIdResultCompletedAt;
import ru.itis.model.stats.UserResultCompletedAt;
import ru.itis.repository.api.UsersCompletedTestsRepository;
import ru.itis.repository.impl.UsersCompletedTestsRepositoryImpl;
import ru.itis.service.test.common.api.TestService;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.user.common.api.UserService;
import ru.itis.service.user.common.impl.BaseUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserCompletedTestsServiceImpl implements UserCompletedTestsService {

    private final UsersCompletedTestsRepository userCompletedTestsRepository;
    private final UserService userService;
    private final TestService testService;

    public UserCompletedTestsServiceImpl() {
        this.userCompletedTestsRepository = new UsersCompletedTestsRepositoryImpl();
        this.userService = new BaseUserService();
        this.testService = new TestServiceImpl();
    }

    @Override
    public void addRelation(UUID userId, UUID testId, LocalDateTime completedAt, int result) {
        userCompletedTestsRepository.addRelation(userId, testId, completedAt, result);
    }

    @Override
    public List<UUID> getTestIds(UUID userId) {
        return userCompletedTestsRepository.getTestIds(userId);
    }

    @Override
    public List<UUID> getUsersIds(UUID testId) {
        return userCompletedTestsRepository.getUsersIds(testId);
    }

    @Override
    public boolean removeRelation(UUID userId, UUID testId) {
        return userCompletedTestsRepository.removeRelation(userId, testId);
    }

    @Override
    public Result getTestResult(UUID userId, UUID testId) {
        return userCompletedTestsRepository.getTestResult(userId, testId).orElseThrow(
                () -> new RuntimeException("No test result found for test " + testId)
        );
    }

    @Override
    public List<UserResultCompletedAt> getUsersScoreCompletedAtByTestId(UUID testId) {
        List<UserIdResultCompletedAt> userIdResultCompletedAtList = userCompletedTestsRepository.getUsersScoreCompletedAtByTestId(testId);
        List<UserResultCompletedAt> userResultCompletedAtList = new ArrayList<>();
        for (UserIdResultCompletedAt userIdResultCompletedAt : userIdResultCompletedAtList) {
            userResultCompletedAtList.add(
                    new UserResultCompletedAt(
                    userService.findById(userIdResultCompletedAt.getUserId()),
                    userIdResultCompletedAt.getScore(),
                    userIdResultCompletedAt.getCompletedAt()));
        }
        return userResultCompletedAtList;
    }

    @Override
    public List<CompletedTestStats> getUsersScoreCompletedAtByUserId(UUID userId) {
        List<CompletedTestIdStats> completedTestIdStatsList = userCompletedTestsRepository.getUsersScoreCompletedAtByUserId(userId);
        List<CompletedTestStats> completedTestStatsList = new ArrayList<>();
        for (CompletedTestIdStats completedTestIdStats : completedTestIdStatsList) {
            CompletedTestStats completedTestStats = new CompletedTestStats(
                    testService.findById(completedTestIdStats.getTestId()),
                    completedTestIdStats.getResult(),
                    completedTestIdStats.getCompletedAt()
            );
            completedTestStatsList.add(completedTestStats);
        }
        return completedTestStatsList;
    }

}
