package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.model.TestResult;
import ru.itis.repository.api.TestResultRepository;
import ru.itis.repository.rowmapper.api.RowMapper;
import ru.itis.repository.rowmapper.impl.TestResultRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestResultRepositoryImpl implements TestResultRepository {

    private final DatabaseConfig databaseConfig;
    private final RowMapper<TestResult> testResultRowMapper;

    public TestResultRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
        this.testResultRowMapper = new TestResultRowMapper();
    }

    //language=sql
    private static final String SQL_FIND_TEST_RESULT_BY_TEST_ID = """
            SELECT *
            FROM test_result
            WHERE test_id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_TEST_RESULT = """
            INSERT INTO test_result(test_id, min_score, max_score, description)
            VALUES(?, ?, ?, ?)
            RETURNING id;
            """;

    //language=sql
    private static final String SQL_DELETE_TEST_RESULT_BY_ID = """
            DELETE FROM test_result
            WHERE id = ?
            """;

    @Override
    public List<TestResult> findByTestId(UUID testId) {
        List<TestResult> testResults = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_TEST_RESULT_BY_TEST_ID)) {
            statement.setObject(1, testId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    testResults.add(testResultRowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return testResults;
    }

    @Override
    public Optional<UUID> save(TestResult testResult, Connection connection) {
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TEST_RESULT)) {
            statement.setObject(1, testResult.getTestId());
            statement.setInt(2, testResult.getMinScore());
            statement.setInt(3, testResult.getMaxScore());
            statement.setString(4, testResult.getDescription());

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
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TEST_RESULT_BY_ID)) {
            statement.setObject(1, uuid);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
