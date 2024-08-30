package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.controllers.FolderController.UpdateFolder;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class UpdateFolderMapper implements RowMapper<UpdateFolder> {
    @Override
    public UpdateFolder map(ResultSet rs, StatementContext ctx) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        String parentId = rs.getString("parent");
        UUID parent = parentId == null ? null : UUID.fromString(parentId);

        return new UpdateFolder(parent, id, name);
    }
}
