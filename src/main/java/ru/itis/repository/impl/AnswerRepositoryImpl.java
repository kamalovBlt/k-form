package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.model.Answer;
import ru.itis.repository.api.AnswerRepository;
import ru.itis.repository.rowmapper.api.RowMapper;
import ru.itis.repository.rowmapper.impl.AnswerRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AnswerRepositoryImpl implements AnswerRepository {

    private final DatabaseConfig databaseConfig;
    private final RowMapper<Answer> mapper;

    public AnswerRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
        this.mapper = new AnswerRowMapper();
    }

    //language=sql
    private static final String SQL_FIND_ANSWER_BY_ID = """
            SELECT *
            FROM answer
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_ANSWER = """
            INSERT INTO answer(question_id, text, is_correct)
            VALUES(?, ?, ?)
            RETURNING id;
            """;

    //language=sql
    private static final String SQL_DELETE_ANSWER_BY_ID = """
            DELETE FROM answer
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_FIND_ALL_ANSWERS_BY_QUESTION_ID = """
            SELECT *
            FROM answer
            WHERE question_id = ?
            """;

    @Override
    public Optional<Answer> findById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ANSWER_BY_ID)) {
            statement.setObject(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapper.mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UUID> save(Answer answer, Connection connection) {
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ANSWER)) {
            statement.setObject(1, answer.getQuestionId());
            statement.setString(2, answer.getText());
            statement.setBoolean(3, answer.isCorrect());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UUID id = resultSet.getObject("id", UUID.class);
                    return Optional.of(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ANSWER_BY_ID)) {
            statement.setObject(1, uuid);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Answer> findAllByQuestionId(UUID questionId) {
        List<Answer> answers = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ANSWERS_BY_QUESTION_ID)) {
            statement.setObject(1, questionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(mapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }
}
