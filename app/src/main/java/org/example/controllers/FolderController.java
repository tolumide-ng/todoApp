package org.example.controllers;

import io.javalin.http.Context;

import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.example.Database;
import org.example.dao.FolderDao;
import org.example.model.Folder;
import org.jdbi.v3.core.Jdbi;

public class FolderController {
    public record CreateFolder(String name, UUID parent) {
    }

    public static void createFolder(Context ctx) {
        Jdbi dbPool = ctx.appData(Database.dbKey());
        FolderDao folderDao = dbPool.onDemand(FolderDao.class);

        // folderDao = dbPool.onDemand(null)

        CreateFolder folder = ctx.bodyValidator(CreateFolder.class)
                .check(data -> data.name != null && !data.name.isEmpty(), "Please provide a valid Folder name").get();

        try (
                Connection conn = Database.connect();
                PreparedStatement st = conn
                        .prepareStatement("INSERT INTO folder (name, parent) VALUES(?, ?) RETURNING *")) {
            st.setString(1, folder.name);
            st.setObject(2, folder.parent);
            ResultSet rs = st.executeQuery();
            rs.next();

            String name = rs.getString("name");
            UUID id = (UUID) rs.getObject("id");
            Folder newFolder = new Folder(name, id, null);

            rs.close();
            ctx.json(newFolder).status(200);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }

    public static void getOneFolder(Context ctx) {
        UUID folderId = UUID.fromString(ctx.pathParam("folderId"));

        Jdbi dbPool = ctx.appData(Database.dbKey());
        FolderDao folderDao = dbPool.onDemand(FolderDao.class);
        Folder folder = folderDao.getOneFolder(folderId);

        ctx.json(folder).status(200);

        // try {
        // List<Folder> folders = new FolderDao().getOneFolder(folderId);

        // ctx.json(folders).status(200);
        // } catch (Exception e) {
        // e.printStackTrace();
        // ctx.json("Internal Server Error").status(500);
        // }
    }

    public static void getFolders(Context ctx) {
        try (
                Connection conn = Database.connect();
                PreparedStatement st = conn.prepareStatement("SELECT id, name FROM folder WHERE parent = NULL")) {
            ResultSet rs = st.executeQuery();
            rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }

    public static void updateFolder(Context ctx) {
        //
    }

    public static void deleteFolder(Context ctx) {
        //
    }

}
