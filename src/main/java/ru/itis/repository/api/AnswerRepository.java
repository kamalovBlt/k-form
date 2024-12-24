package ru.itis.repository.api;

import ru.itis.model.Answer;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository {

    Optional<Answer> findById(UUID uuid);

    Optional<UUID> save(Answer answer, Connection connection);

    boolean deleteById(UUID uuid);

    List<Answer> findAllByQuestionId(UUID questionId);

}
