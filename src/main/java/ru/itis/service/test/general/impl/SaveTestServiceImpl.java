package ru.itis.service.test.general.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.config.DatabaseConfig;
import ru.itis.dto.AnswerDTO;
import ru.itis.dto.QuestionDTO;
import ru.itis.dto.TestDTO;
import ru.itis.dto.TestResultDTO;
import ru.itis.model.*;
import ru.itis.service.mapper.Mapper;
import ru.itis.service.test.common.api.AnswerService;
import ru.itis.service.test.common.api.QuestionService;
import ru.itis.service.test.common.api.TestResultService;
import ru.itis.service.test.common.api.TestService;
import ru.itis.service.test.common.impl.AnswerServiceImpl;
import ru.itis.service.test.common.impl.QuestionServiceImpl;
import ru.itis.service.test.common.impl.TestResultServiceImpl;
import ru.itis.service.test.common.impl.TestServiceImpl;
import ru.itis.service.test.general.api.SaveTestService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SaveTestServiceImpl implements SaveTestService {

    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestResultService testResultService;
    private final ObjectMapper jsonMapper;
    private final Mapper mapper;
    private final DatabaseConfig databaseConfig;

    public SaveTestServiceImpl() {
        this.testService = new TestServiceImpl();
        this.questionService = new QuestionServiceImpl();
        this.answerService = new AnswerServiceImpl();
        this.testResultService = new TestResultServiceImpl();
        this.jsonMapper = new ObjectMapper();
        this.mapper = new Mapper();
        this.databaseConfig = DatabaseConfig.getInstance();
    }

    @Override
    public void save(InputStream jsonObjects, UUID userId) throws IOException {

        try (Connection connection = databaseConfig.getConnection()) {

            connection.setAutoCommit(false);

            TestDTO testDTO = jsonMapper.readValue(jsonObjects, TestDTO.class);
            Test test = mapper.toTestEntity(testDTO);
            test.setUserId(userId);
            UUID testId = testService.save(test, connection);
            List<QuestionDTO> questionDTOS = testDTO.getQuestions();
            for (QuestionDTO questionDTO : questionDTOS) {
                System.out.println(questionDTO.getScore());
                List<AnswerDTO> answerDTOS = questionDTO.getAnswers();
                Question question = mapper.toQuestionEntity(questionDTO);
                question.setTestId(testId);
                UUID questionId = questionService.save(question, connection);
                for (AnswerDTO answerDTO : answerDTOS) {
                    Answer answer = mapper.toAnswerEntity(answerDTO);
                    answer.setQuestionId(questionId);
                    answerService.save(answer, connection);
                }
            }
            List<TestResultDTO> testResultDTO = testDTO.getTestResults();

            for (TestResultDTO resultDTO : testResultDTO) {
                TestResult testResult = mapper.toTestResultEntity(resultDTO);
                testResult.setTestId(testId);
                testResultService.save(testResult, connection);
            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
