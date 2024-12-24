package ru.itis.repository.rowmapper.impl;

import ru.itis.model.Test;
import ru.itis.repository.rowmapper.api.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TestRowMapper implements RowMapper<Test> {
    @Override
    public Test mapRow(ResultSet resultSet) throws SQLException {
        return new Test(
                resultSet.getObject("id", UUID.class),
                resultSet.getObject("user_id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getString("category"),
                resultSet.getObject("created_at", java.time.LocalDateTime.class),
                resultSet.getInt("max_ball")
        );
    }
}
