package ru.itis.service.test.common.impl;

import ru.itis.model.Question;
import ru.itis.repository.api.QuestionRepository;
import ru.itis.repository.impl.QuestionRepositoryImpl;
import ru.itis.exception.EntityNotFoundException;
import ru.itis.exception.IdNotReturnsException;
import ru.itis.service.test.common.api.QuestionService;

import java.util.List;
import java.util.UUID;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl() {
        this.questionRepository = new QuestionRepositoryImpl();
    }

    @Override
    public Question findById(UUID uuid) {
        return questionRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Question", uuid)
        );
    }

    @Override
    public UUID save(Question question) {
        return questionRepository.save(question).orElseThrow(
                () -> new IdNotReturnsException("Question")
        );
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return questionRepository.deleteById(uuid);
    }

    @Override
    public List<Question> findAllByTestId(UUID testId) {
        return questionRepository.findAllByTestId(testId);
    }
}
