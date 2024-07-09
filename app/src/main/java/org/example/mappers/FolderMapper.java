package org.example.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.model.Folder;
import org.example.model.Task;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FolderMapper implements RowMapper<Folder> {
    @Override
    public Folder map(ResultSet rs, StatementContext ctx) throws SQLException {

        Task[] jsonFiles = parseFiles(rs.getString("files"));
        String name = rs.getString("name");
        UUID id = UUID.fromString(rs.getString("id"));

        return new Folder(name, id, jsonFiles);
    }

    private Task[] parseFiles(String jsonFiles) {
        // Parse the JSON array to create File objects
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonFiles, Task[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON files");
        }
    }
}
