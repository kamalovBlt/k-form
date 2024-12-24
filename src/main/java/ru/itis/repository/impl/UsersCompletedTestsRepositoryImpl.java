package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.model.Result;
import ru.itis.model.stats.CompletedTestIdStats;
import ru.itis.model.stats.UserIdResultCompletedAt;
import ru.itis.repository.api.UsersCompletedTestsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class UsersCompletedTestsRepositoryImpl implements UsersCompletedTestsRepository {

    private final DatabaseConfig databaseConfig;

    public UsersCompletedTestsRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
    }

    //language=sql
    private static final String SQL_GET_COMPLETED_TESTS_BY_USER_ID = """
            SELECT test_id
            FROM users_completed_tests
            WHERE user_id = ?
            """;

    //language=sql
    private static final String SQL_GET_USERS_BY_TEST_ID = """
            SELECT user_id
            FROM users_completed_tests
            WHERE test_id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_USER_COMPLETED_TEST = """
            INSERT INTO users_completed_tests(user_id, test_id, completed_at, result)
            VALUES(?, ?, ?, ?)
            """;

    //language=sql
    private static final String SQL_DELETE_USER_COMPLETED_TEST_BY_IDS = """
            DELETE FROM users_completed_tests
            WHERE user_id = ? AND test_id = ?
            """;

    //language=sql
    private static final String SQL_GET_RESULT_BY_TEST_AND_USER_ID = """
            SELECT result, completed_at
            FROM users_completed_tests
            WHERE user_id = ? AND test_id = ?
            """;

    //language=sql
    private static final String SQL_GET_USER_RESULT_COMPLETED_AT_BY_TEST_ID = """
            SELECT *
            FROM users_completed_tests
            WHERE test_id = ?
            """;

    //language=sql
    private static final String SQL_GET_TEST_RESULT_COMPLETED_AT_BY_USER_ID = """
            SELECT *
            FROM users_completed_tests
            WHERE user_id = ?
            """;

    @Override
    public void addRelation(UUID userId, UUID testId, LocalDateTime completedAt, int result) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_COMPLETED_TEST)) {
            statement.setObject(1, userId);
            statement.setObject(2, testId);
            statement.setObject(3, completedAt);
            statement.setInt(4, result);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add relation", e);
        }
    }

    @Override
    public List<UUID> getTestIds(UUID userId) {
        List<UUID> testIds = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_COMPLETED_TESTS_BY_USER_ID)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    testIds.add(resultSet.getObject("test_id", UUID.class));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get test IDs", e);
        }
        return testIds;
    }

    @Override
    public List<UUID> getUsersIds(UUID testId) {
        List<UUID> userIds = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USERS_BY_TEST_ID)) {
            statement.setObject(1, testId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userIds.add(resultSet.getObject("user_id", UUID.class));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user IDs", e);
        }
        return userIds;
    }

    @Override
    public boolean removeRelation(UUID userId, UUID testId) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_COMPLETED_TEST_BY_IDS)) {
            statement.setObject(1, userId);
            statement.setObject(2, testId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove relation", e);
        }
    }

    @Override
    public Optional<Result> getTestResult(UUID userId, UUID testId) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_RESULT_BY_TEST_AND_USER_ID)) {
            statement.setObject(1, userId);
            statement.setObject(2, testId);
            try (ResultSet resultSet = statement.executeQuery()) {

                List<Result> results = new ArrayList<>();

                while (resultSet.next()) {
                    results.add(Result.builder()
                            .score(resultSet.getInt("result"))
                            .completedAt(resultSet.getTimestamp("completed_at").toLocalDateTime())
                            .build());
                }

                return results.stream()
                        .max(Comparator.comparing(Result::getCompletedAt));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get test result", e);
        }
    }

    @Override
    public List<UserIdResultCompletedAt> getUsersScoreCompletedAtByTestId(UUID testId) {

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_RESULT_COMPLETED_AT_BY_TEST_ID)) {
            statement.setObject(1, testId);
            try (ResultSet resultSet = statement.executeQuery()) {

                List<UserIdResultCompletedAt> results = new ArrayList<>();

                while (resultSet.next()) {
                    UserIdResultCompletedAt userIdResultCompletedAt = new UserIdResultCompletedAt(
                            resultSet.getObject("user_id", UUID.class),
                            resultSet.getInt("result"),
                            resultSet.getTimestamp("completed_at").toLocalDateTime()
                    );
                    results.add(userIdResultCompletedAt);
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<CompletedTestIdStats> getUsersScoreCompletedAtByUserId(UUID userId) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TEST_RESULT_COMPLETED_AT_BY_USER_ID)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {

                List<CompletedTestIdStats> results = new ArrayList<>();

                while (resultSet.next()) {
                    CompletedTestIdStats completedTestIdStats = new CompletedTestIdStats(
                            resultSet.getObject("test_id", UUID.class),
                            resultSet.getInt("result"),
                            resultSet.getTimestamp("completed_at").toLocalDateTime()
                    );
                    results.add(completedTestIdStats);
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}