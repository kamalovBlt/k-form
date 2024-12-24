package ru.itis.repository.rowmapper.impl;

import ru.itis.model.Answer;
import ru.itis.repository.rowmapper.api.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet resultSet) throws SQLException {
        return new Answer(
                resultSet.getObject("id", UUID.class),
                resultSet.getObject("question_id", UUID.class),
                resultSet.getString("text"),
                resultSet.getBoolean("is_correct")
        );
    }
}
