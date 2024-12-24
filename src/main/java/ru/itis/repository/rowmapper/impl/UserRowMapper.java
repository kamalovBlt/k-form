package ru.itis.repository.rowmapper.impl;

import ru.itis.model.User;
import ru.itis.repository.rowmapper.api.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
