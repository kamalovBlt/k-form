package ru.itis.service.mapper;

import ru.itis.dto.AnswerDTO;
import ru.itis.dto.QuestionDTO;
import ru.itis.dto.TestDTO;
import ru.itis.dto.TestResultDTO;
import ru.itis.model.Answer;
import ru.itis.model.Question;
import ru.itis.model.Test;
import ru.itis.model.TestResult;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public Test toTestEntity(TestDTO testDTO) {
        return Test.builder()
                .title(testDTO.getTitle())
                .description(testDTO.getDescription())
                .category(testDTO.getCategory())
                .maxBall(testDTO.getMaxBall())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Question toQuestionEntity(QuestionDTO questionDTO) {
        return Question.builder()
                .number(questionDTO.getNumber())
                .text(questionDTO.getText())
                .score(questionDTO.getScore())
                .build();
    }
    public Answer toAnswerEntity(AnswerDTO answerDTO) {
        return Answer.builder()
                .text(answerDTO.getText())
                .isCorrect(answerDTO.isCorrect())
                .build();
    }
    public TestResult toTestResultEntity(TestResultDTO testResultDTO) {
        return TestResult.builder()
                .description(testResultDTO.getDescription())
                .minScore(testResultDTO.getMinScore())
                .maxScore(testResultDTO.getMaxScore())
                .build();
    }

    public TestDTO totestDTO(Test test, List<Question> questions, List<Answer> answers, List<TestResult> testResults) {

        TestDTO testDTO = new TestDTO();
        testDTO.setTitle(test.getTitle());
        testDTO.setDescription(test.getDescription());
        testDTO.setCategory(test.getCategory());
        testDTO.setMaxBall(test.getMaxBall());

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = toQuestionDTO(question);
            questionDTOs.add(questionDTO);
            List<AnswerDTO> answerDTOs = new ArrayList<>();
            for (Answer answer : answers) {
                if (question.getId().equals(answer.getQuestionId())) {
                    answerDTOs.add(toAnswerDTO(answer));
                }
            }
            questionDTO.setAnswers(answerDTOs);
        }
        testDTO.setQuestions(questionDTOs);

        List<TestResultDTO> testResultDTOs = new ArrayList<>();
        for (TestResult testResult : testResults) {
            testResultDTOs.add(toTestResultDTO(testResult));
        }
        testDTO.setTestResults(testResultDTOs);

        return testDTO;
    }

    public TestResultDTO toTestResultDTO(TestResult testResult) {
        TestResultDTO testResultDTO = new TestResultDTO();
        testResultDTO.setDescription(testResult.getDescription());
        testResultDTO.setMinScore(testResult.getMinScore());
        testResultDTO.setMaxScore(testResult.getMaxScore());
        return testResultDTO;
    }

    public QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setNumber(question.getNumber());
        questionDTO.setText(question.getText());
        questionDTO.setScore(question.getScore());
        return questionDTO;
    }

    public AnswerDTO toAnswerDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setText(answer.getText());
        answerDTO.setCorrect(answer.isCorrect());
        return answerDTO;
    }

}
