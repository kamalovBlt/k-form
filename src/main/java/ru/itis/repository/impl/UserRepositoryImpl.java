package ru.itis.repository.impl;

import ru.itis.config.DatabaseConfig;
import ru.itis.exception.EmailAlreadyExistsException;
import ru.itis.model.User;
import ru.itis.repository.api.UserRepository;
import ru.itis.repository.rowmapper.api.RowMapper;
import ru.itis.repository.rowmapper.impl.UserRowMapper;
import ru.itis.service.BCryptService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private final DatabaseConfig databaseConfig;
    private final RowMapper<User> userRowMapper;

    public UserRepositoryImpl() {
        this.databaseConfig = DatabaseConfig.getInstance();
        this.userRowMapper = new UserRowMapper();
    }

    //language=sql
    private static final String SQL_FIND_USER_BY_EMAIL = """
            SELECT *
            FROM users
            WHERE email = ?
            """;

    //language=sql
    private static final String SQL_FIND_USER_BY_ID = """
            SELECT *
            FROM users
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_INSERT_USER = """
            INSERT INTO users(name, surname, email, password)
            VALUES(?, ?, ?, ?)
            RETURNING id;
            """;

    //language=sql
    private static final String SQL_UPDATE_USER_BY_ID = """
            UPDATE users
            SET name = ?, surname = ?, email = ?, password = ?
            WHERE id = ?
            """;

    //language=sql
    private static final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM users
            WHERE id = ?
            """;

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            return getUser(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setObject(1, uuid);
            return getUser(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UUID> save(User user) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, BCryptService.encryptPassword(user.getPassword()));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(resultSet.getObject("id", UUID.class));
                }
            }
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new EmailAlreadyExistsException();
            }
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateByUserId(User user) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, BCryptService.encryptPassword(user.getPassword()));
            statement.setObject(5, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID uuid) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            statement.setObject(1, uuid);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<User> getUser(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? Optional.ofNullable(userRowMapper.mapRow(resultSet)) : Optional.empty();
        }
    }
}
