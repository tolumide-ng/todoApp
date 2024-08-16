package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.model.File;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class FileInsert implements RowMapper<File> {
    @Override
    public File map(ResultSet rs, StatementContext ctx) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        UUID parent = UUID.fromString(rs.getString("parent"));
        String name = rs.getString("name");

        return new File(name, id, parent, "");
    }
}
