package org.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.example.utils.Folder;
import org.example.utils.Task;

public interface Helper {
    default Folder getFolder(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        UUID id = (UUID) resultSet.getObject("id");
        Task[] files = resultSet.getObject("children", Task[].class);

        return new Folder(name, id, files);
    }
}
