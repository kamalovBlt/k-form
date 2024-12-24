package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.model.Question;
import ru.itis.repository.api.QuestionRepository;
import ru.itis.repository.rowmapper.api.RowMapper;
import ru.itis.repository.rowmapper.impl.QuestionRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QuestionRepositoryImpl implements QuestionRepository {

    private final DatabaseConfig databaseConfig;
    private final RowMapper<Question> questionRowMapper;

    public QuestionRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
        this.questionRowMapper = new QuestionRowMapper();
    }

    //language=sql
    private static final String SQL_FIND_QUESTIONS_BY_TEST_ID = """
            SELECT *
            FROM question
            WHERE test_id = ?
            """;

    //language=sql
    private static final String SQL_FIND_QUESTIONS_BY_ID = """
            SELECT *
            FROM question
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_QUESTION = """
            INSERT INTO question(test_id, number, text, score)
            VALUES(?, ?, ?, ?)
            RETURNING id;
            """;

    //language=sql
    private static final String SQL_DELETE_QUESTION_BY_ID = """
            DELETE FROM question
            WHERE id = ?
            """;

    @Override
    public Optional<Question> findById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_QUESTIONS_BY_ID)) {
            statement.setObject(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(questionRowMapper.mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UUID> save(Question question) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_QUESTION)) {
            statement.setObject(1, question.getTestId());
            statement.setInt(2, question.getNumber());
            statement.setString(3, question.getText());
            statement.setInt(4, question.getScore());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UUID id = resultSet.getObject("id", UUID.class);
                    return Optional.ofNullable(id);
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
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_QUESTION_BY_ID)) {
            statement.setObject(1, uuid);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> findAllByTestId(UUID testId) {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_QUESTIONS_BY_TEST_ID)) {
            statement.setObject(1, testId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(questionRowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
