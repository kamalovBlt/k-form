package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.model.Test;
import ru.itis.repository.api.TestRepository;
import ru.itis.repository.rowmapper.api.RowMapper;
import ru.itis.repository.rowmapper.impl.TestRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestRepositoryImpl implements TestRepository {

    private final DatabaseConfig databaseConfig;
    private final RowMapper<Test> testRowMapper;

    public TestRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
        this.testRowMapper = new TestRowMapper();
    }

    //language=sql
    private static final String SQL_FIND_TEST_BY_ID = """
            SELECT *
            FROM test
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_TEST = """
            INSERT INTO test(user_id, title, description, category, created_at, max_ball)
            VALUES(?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    //language=sql
    private static final String SQL_DELETE_TEST_BY_ID = """
            DELETE FROM test
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_FIND_ALL_TESTS = """
            SELECT *
            FROM test
            """;

    //language=sql
    private static final String SQL_FIND_ALL_TESTS_BY_CATEGORY = """
            SELECT *
            FROM test
            WHERE category = ?
            """;

    //language=sql
    private static final String SQL_COUNT_OF_CREATED_TESTS_BY_USER_ID = """
            SELECT COUNT(*)
            FROM test
            WHERE user_id = ?
            """;

    //language=sql
    private static final String SQL_CREATED_TESTS_BY_USER_ID = """
            SELECT *
            FROM test
            WHERE user_id = ?
            """;

    @Override
    public List<Test> findAll() {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_TESTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tests.add(testRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tests;
    }

    @Override
    public List<Test> findByCategory(String category) {
        List<Test> tests = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_TESTS_BY_CATEGORY)) {
            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tests.add(testRowMapper.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tests;
    }

    @Override
    public Optional<Test> findById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_TEST_BY_ID)) {
            statement.setObject(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(testRowMapper.mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UUID> save(Test test, Connection connection) {
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TEST)) {
            statement.setObject(1, test.getUserId());
            statement.setString(2, test.getTitle());
            statement.setString(3, test.getDescription());
            statement.setString(4, test.getCategory());
            statement.setObject(5, test.getCreatedAt());
            statement.setInt(6, test.getMaxBall());
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
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TEST_BY_ID)) {
            statement.setObject(1, uuid);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countOfCreatedTestsByUser(UUID userId) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_OF_CREATED_TESTS_BY_USER_ID)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<UUID> findCreatedTestsByUser(UUID userId) {
        List<UUID> tests = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATED_TESTS_BY_USER_ID)) {
            statement.setObject(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tests.add(resultSet.getObject("id", UUID.class));
                }
            }
            return tests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}