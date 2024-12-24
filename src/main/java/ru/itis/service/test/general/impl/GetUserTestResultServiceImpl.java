package ru.itis.service.test.general.impl;

import ru.itis.model.Result;
import ru.itis.model.TestResult;
import ru.itis.service.test.common.api.TestResultService;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.test.common.impl.TestResultServiceImpl;
import ru.itis.service.test.common.impl.UserCompletedTestsServiceImpl;
import ru.itis.service.test.general.api.GetUserTestResultService;

import java.util.List;
import java.util.UUID;

public class GetUserTestResultServiceImpl implements GetUserTestResultService {

    private final TestResultService testResultService;
    private final UserCompletedTestsService userCompletedTestsService;

    public GetUserTestResultServiceImpl() {
        this.testResultService = new TestResultServiceImpl();
        this.userCompletedTestsService = new UserCompletedTestsServiceImpl();
    }

    @Override
    public Result getUserTestResult(UUID userId, UUID testId) {
        Result result = userCompletedTestsService.getTestResult(userId, testId);
        List<TestResult> testResult = testResultService.findByTestId(testId);
        int score = result.getScore();
        testResult.forEach(t -> {
            if (t.getMinScore() <= score && t.getMaxScore() >= score) {
                 result.setDescription(t.getDescription());
            }
        });
        return result;
    }
}
