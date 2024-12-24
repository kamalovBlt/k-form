package ru.itis.service.test.general.impl;

import ru.itis.dto.TestDTO;
import ru.itis.model.Answer;
import ru.itis.model.Question;
import ru.itis.model.Test;
import ru.itis.model.TestResult;
import ru.itis.service.mapper.Mapper;
import ru.itis.service.test.common.api.AnswerService;
import ru.itis.service.test.common.api.QuestionService;
import ru.itis.service.test.common.api.TestResultService;
import ru.itis.service.test.common.api.TestService;
import ru.itis.service.test.common.impl.AnswerServiceImpl;
import ru.itis.service.test.common.impl.QuestionServiceImpl;
import ru.itis.service.test.common.impl.TestResultServiceImpl;
import ru.itis.service.test.common.impl.TestServiceImpl;
import ru.itis.service.test.general.api.GetTestService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetTestServiceImpl implements GetTestService {

    private final Mapper mapper;
    private final TestService testService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final TestResultService testResultService;

    public GetTestServiceImpl() {
        mapper = new Mapper();
        testService = new TestServiceImpl();
        answerService = new AnswerServiceImpl();
        questionService = new QuestionServiceImpl();
        testResultService = new TestResultServiceImpl();
    }

    @Override
    public TestDTO getTestById(UUID testId) {
        Test test = testService.findById(testId);
        List<Question> questions = questionService.findAllByTestId(testId);
        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            List<Answer> actualAnswers = answerService.findAllByQuestionId(question.getId());
            answers.addAll(actualAnswers);
        }
        List<TestResult> testResults = testResultService.findByTestId(testId);
        return mapper.totestDTO(test, questions, answers, testResults);
    }
}
