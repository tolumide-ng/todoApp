package org.example.controllers;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.example.Database;
import org.example.dao.FolderDao;
import org.example.model.Folder;
import org.example.response.ApiResponse;
import org.example.response.ErrorResponse;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import java.sql.*;;

public class FolderController {

    public record CreateFolder(String name, UUID parent) {
    }

    public static void createFolder(Context ctx) {
        // Jdbi dbPool = ctx.appData(Database.dbKey());
        // FolderDao folderDao = dbPool.onDemand(FolderDao.class);

        // // folderDao = dbPool.onDemand(null)

        // CreateFolder folder = ctx.bodyValidator(CreateFolder.class)
        // .check(data -> data.name != null && !data.name.isEmpty(), "Please provide a
        // valid Folder name").get();

        // try (
        // Connection conn = Database.connect();
        // PreparedStatement st = conn
        // .prepareStatement("INSERT INTO folder (name, parent) VALUES(?, ?) RETURNING
        // *")) {
        // st.setString(1, folder.name);
        // st.setObject(2, folder.parent);
        // ResultSet rs = st.executeQuery();
        // rs.next();

        // String name = rs.getString("name");
        // UUID id = (UUID) rs.getObject("id");
        // Folder newFolder = new Folder(name, id, null);

        // rs.close();
        // ctx.json(newFolder).status(200);
        // } catch (SQLException e) {
        // e.printStackTrace();
        // ctx.json("Internal Server Error").status(500);
        // }
    }

    public static void getOneFolder(Context ctx) {
        System.out.println("was here in getOneFolder>>>>>");
        // try {
        // UUID folderId = UUID.fromString(ctx.pathParam("folderId"));

        // } catch (Exception e) {
        // // ctx.json()
        // System.out.println("received an exception " + e);
        // ApiResponse response = new ApiResponse(400, "welcome", "");
        // ctx.json(response).status(400);
        // // ctx.json(new BadRequestResponse()).status(200);
        // return;
        // }

        UUID folderId = UUID.fromString(ctx.pathParam("folderId"));

        System.out.println("the folderId =" + folderId);

        Jdbi dbPool = ctx.appData(Database.dbKey());
        // Jdbi dbPool = Database.getJdbi();
        Folder folder = dbPool.withHandle(handle -> {
            FolderDao dao = handle.attach(FolderDao.class);
            return dao.getOneFolder(folderId);
        });

        if (folder == null) {
            // throw new NotFoundResponse("Folder not found");
            ctx.json(new ErrorResponse(404, "Not Found", "Folder not found"));
            return;
        }

        System.out.println("non null " + folder);
        ctx.json(folder).status(200);

    }

    public static void getFolders(Context ctx) {
        System.out.println("getFoldersgetFoldersgetFoldersgetFolders--getFoldersgetFolders>>>>>");

        // try (
        // Connection conn = Database.connect();
        // PreparedStatement st = conn.prepareStatement("SELECT id, name FROM folder
        // WHERE parent = NULL")) {
        // ResultSet rs = st.executeQuery();
        // rs.next();
        // } catch (Exception e) {
        // e.printStackTrace();
        // ctx.json("Internal Server Error").status(500);
        // }
    }

    public static void updateFolder(Context ctx) {
        //
    }

    public static void deleteFolder(Context ctx) {
        //
    }

}
