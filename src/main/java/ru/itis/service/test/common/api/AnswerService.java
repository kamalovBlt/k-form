package ru.itis.service.test.common.api;

import ru.itis.model.Answer;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public interface AnswerService {

    Answer findById(UUID uuid);

    UUID save(Answer answer, Connection connection);

    boolean deleteById(UUID uuid);

    List<Answer> findAllByQuestionId(UUID questionId);

}
