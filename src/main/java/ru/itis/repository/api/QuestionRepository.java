package ru.itis.repository.api;

import ru.itis.model.Answer;
import ru.itis.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {

    Optional<Question> findById(UUID uuid);

    Optional<UUID> save(Question question);

    boolean deleteById(UUID uuid);

    List<Question> findAllByTestId(UUID testId);

}
