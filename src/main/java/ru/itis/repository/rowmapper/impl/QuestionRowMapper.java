package ru.itis.repository.rowmapper.impl;

import ru.itis.model.Question;
import ru.itis.repository.rowmapper.api.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet resultSet) throws SQLException {
        return new Question(
                resultSet.getObject("id", UUID.class),
                resultSet.getObject("test_id", UUID.class),
                resultSet.getInt("number"),
                resultSet.getString("text"),
                resultSet.getInt("score")
        );
    }
}
