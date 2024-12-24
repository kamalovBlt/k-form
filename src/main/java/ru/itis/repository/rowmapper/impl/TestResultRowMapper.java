package ru.itis.repository.rowmapper.impl;

import ru.itis.model.TestResult;
import ru.itis.repository.rowmapper.api.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TestResultRowMapper implements RowMapper<TestResult> {
    @Override
    public TestResult mapRow(ResultSet resultSet) throws SQLException {
        return new TestResult(
                resultSet.getObject("id", UUID.class),
                resultSet.getObject("test_id", UUID.class),
                resultSet.getInt("min_score"),
                resultSet.getInt("max_score"),
                resultSet.getString("description")
        );
    }
}
