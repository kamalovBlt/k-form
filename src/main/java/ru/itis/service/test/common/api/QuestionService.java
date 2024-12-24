package ru.itis.service.test.common.api;

import ru.itis.model.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    Question findById(UUID uuid);

    UUID save(Question question);

    boolean deleteById(UUID uuid);

    List<Question> findAllByTestId(UUID testId);

}
