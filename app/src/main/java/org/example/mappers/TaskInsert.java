package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.model.Task;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class TaskInsert implements RowMapper<Task> {
    @Override
    public Task map(ResultSet rs, StatementContext ctx) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        UUID parent = UUID.fromString(rs.getString("parent"));
        String name = rs.getString("name");

        return new Task(name, id, parent, "");
    }
}
