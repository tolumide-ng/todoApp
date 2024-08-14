package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.model.Task;
import org.jdbi.v3.core.statement.StatementContext;

public class TaskMapper implements org.jdbi.v3.core.mapper.RowMapper<Task> {
    @Override
    public Task map(ResultSet rs, StatementContext ctx) throws SQLException {
        String name = rs.getString("name");
        UUID id = UUID.fromString(rs.getString("id"));
        UUID parent = UUID.fromString(rs.getString("parent"));

        return new Task(name, id, parent, null);
    }
}
