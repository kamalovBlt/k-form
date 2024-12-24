package ru.itis.service.test.common.impl;

import ru.itis.model.Answer;
import ru.itis.repository.api.AnswerRepository;
import ru.itis.repository.impl.AnswerRepositoryImpl;
import ru.itis.exception.EntityNotFoundException;
import ru.itis.exception.IdNotReturnsException;
import ru.itis.service.test.common.api.AnswerService;

import java.util.List;
import java.util.UUID;

public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl() {
        this.answerRepository = new AnswerRepositoryImpl();
    }

    @Override
    public Answer findById(UUID uuid) {
        return answerRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Answer", uuid)
        );
    }

    @Override
    public UUID save(Answer answer) {
        return answerRepository.save(answer).orElseThrow(
                () -> new IdNotReturnsException("Answer")
        );
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return answerRepository.deleteById(uuid);
    }

    @Override
    public List<Answer> findAllByQuestionId(UUID questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }
}
