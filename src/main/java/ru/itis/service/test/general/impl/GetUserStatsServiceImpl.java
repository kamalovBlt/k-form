package ru.itis.service.test.general.impl;

import ru.itis.model.*;
import ru.itis.model.stats.CompletedTestStats;
import ru.itis.model.stats.CreatedTestStats;
import ru.itis.model.stats.UserResultCompletedAt;
import ru.itis.service.test.common.api.TestService;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.test.common.impl.TestServiceImpl;
import ru.itis.service.test.common.impl.UserCompletedTestsServiceImpl;
import ru.itis.service.test.general.api.GetUserStatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetUserStatsServiceImpl implements GetUserStatsService {

    private final UserCompletedTestsService userCompletedTestsService;
    private final TestService testService;

    public GetUserStatsServiceImpl() {
        userCompletedTestsService = new UserCompletedTestsServiceImpl();
        testService = new TestServiceImpl();
    }

    @Override
    public int getTakeTestsCount(UUID userId) {
        return userCompletedTestsService.getTestIds(userId).size();
    }

    @Override
    public int getCreateTestsCount(UUID userId) {
        return testService.countOfCreatedTestsByUser(userId);
    }

    @Override
    public List<CreatedTestStats> getCreatedTestStats(UUID userId) {

        List<UUID> userCreatedTestIds = testService.findCreatedTestsByUser(userId);
        List<Test> userCreatedTests = new ArrayList<>();
        for (UUID testId : userCreatedTestIds) {
            userCreatedTests.add(testService.findById(testId));
        }
        List<CreatedTestStats> createdTestStats = new ArrayList<>();
        for (Test test : userCreatedTests) {
            List<UserResultCompletedAt> userResultCompletedAtList = userCompletedTestsService.getUsersScoreCompletedAtByTestId(test.getId());
            createdTestStats.add(new CreatedTestStats(test, userResultCompletedAtList));
        }
        return createdTestStats;
    }

    @Override
    public List<CompletedTestStats> getUsersScoreCompletedAtByUserId(UUID userId) {
        return userCompletedTestsService.getUsersScoreCompletedAtByUserId(userId);
    }


}
