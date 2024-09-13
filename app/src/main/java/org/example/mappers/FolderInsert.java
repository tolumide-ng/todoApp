package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.model.Folder;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class FolderInsert implements RowMapper<Folder> {
    @Override
    public Folder map(ResultSet rs, StatementContext ctx) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");

        return new Folder(name, id, null, null);
    }
}
