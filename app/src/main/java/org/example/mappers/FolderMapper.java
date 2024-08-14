package org.example.mappers;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.model.Folder;
import org.example.model.Task;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class FolderMapper implements RowMapper<Folder> {
    @Override
    public Folder map(ResultSet rs, StatementContext ctx) throws SQLException {

        String uuid = rs.getString("id");
        UUID id = uuid == null ? null : UUID.fromString(uuid);
        String name = rs.getString("name");

        // Extract folders
        List<Folder> folders = new ArrayList<>();
        Array foldersArray = rs.getArray("folders");
        if (foldersArray != null) {
            ResultSet folderResultSet = foldersArray.getResultSet();
            while (folderResultSet.next()) {
                Array folderArray = folderResultSet.getArray(2);
                String[] folderData = folderArray.toString().split(",");
                String folderName = folderData[0];
                UUID folderId = folderData.length > 1 ? UUID.fromString(folderData[1]) : null;
                folders.add(new Folder(folderName, folderId, new Folder[] {}, new Task[] {}));
            }
        }

        // Extract files
        List<Task> files = new ArrayList<>();
        Array filesArray = rs.getArray("files");
        if (filesArray != null) {
            ResultSet filesResultSet = filesArray.getResultSet();
            while (filesResultSet.next()) {
                Array fileArray = filesResultSet.getArray(2);
                String[] fileData = fileArray.toString().split(",");
                UUID filesId = UUID.fromString(fileData[1]);
                files.add(new Task(fileData[0], filesId, id, null));
            }
        }

        return new Folder(name, id, folders.toArray(new Folder[0]), files.toArray(new Task[0]));
    }
}
