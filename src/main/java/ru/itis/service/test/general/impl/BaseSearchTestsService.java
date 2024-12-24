package ru.itis.service.test.general.impl;

import ru.itis.model.Test;
import ru.itis.service.test.common.api.TestService;
import ru.itis.service.test.common.api.UserCompletedTestsService;
import ru.itis.service.test.common.impl.TestServiceImpl;
import ru.itis.service.test.common.impl.UserCompletedTestsServiceImpl;
import ru.itis.service.test.general.api.SearchTestsService;

import java.util.*;

public class BaseSearchTestsService implements SearchTestsService {

    private final TestService testService;
    private final UserCompletedTestsService userCompletedTestsService;

    public BaseSearchTestsService() {
        this.testService = new TestServiceImpl();
        this.userCompletedTestsService = new UserCompletedTestsServiceImpl();
    }

    @Override
    public List<Test> getTestForRecommendations(UUID userId) {

        List<Test> foundedTests = new ArrayList<>();
        List<UUID> userCompletedTestIds = userCompletedTestsService.getTestIds(userId);

        List<String> userCompletedTestCategories = new ArrayList<>();
        for (UUID testId : userCompletedTestIds) {
            userCompletedTestCategories.add(testService.findById(testId).getCategory());
        }

        if (userCompletedTestCategories.isEmpty()) {
            return testService.findAll();
        }

        Map<String, Integer> categoryFrequency = new HashMap<>();
        for (String category : userCompletedTestCategories) {
            categoryFrequency.put(category, categoryFrequency.getOrDefault(category, 0) + 1);
        }

        String mostFrequentCategory = null;
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : categoryFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentCategory = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        if (mostFrequentCategory != null) {
            foundedTests = testService.findByCategory(mostFrequentCategory);
        }

        return foundedTests;
    }


    @Override
    public List<Test> getTestOfUserRequest(String request) {

        String[] requests = request.split(" ");

        List<Test> foundedTests = new ArrayList<>();

        int count = 0;
        List<Test> allTests = testService.findAll();
        for (Test test : allTests) {
            String testTitle = test.getTitle();
            String testDescription = test.getDescription();
            for (String actualRequest : requests) {
                String testTitleLowerCase = testTitle.toLowerCase();
                String testDescriptionLowerCase = testDescription.toLowerCase();
                if (testTitleLowerCase.contains(actualRequest.toLowerCase())
                        || testDescriptionLowerCase.contains(actualRequest.toLowerCase())) {
                    foundedTests.add(test);
                    count++;
                    break;
                }
            }
            if (count > 10) {
                break;
            }
        }

        return foundedTests;

    }
}
