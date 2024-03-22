package org.example.controllers;

import io.javalin.http.Context;
import java.util.UUID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.example.DB;
import org.example.controllers.TaskController.Task;

public class FolderController {
    // Task task = TaskController.Task("", UUID.randomUUID());
    public record Folder(String name, UUID id, Task[] tasks, Folder[] children) {
    }

    public record CreateFolder(String name, UUID parent) {
    }

    public static void createFolder(Context ctx) {
        CreateFolder folder = ctx.bodyValidator(CreateFolder.class)
                .check(data -> data.name != null && !data.name.isEmpty(), "Please provide a valid Folder name").get();

        try (
            Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("INSERT INTO folder (name, parent) VALUES(?, ?) RETURNING *")
        ) {
            st.setString(1, folder.name);
            st.setObject(2, folder.parent);
            ResultSet rs = st.executeQuery();
            rs.next();

            String name = rs.getString("name");
            UUID id = (UUID) rs.getObject("id");
            Folder newFolder = new Folder(name, id, null, null);

            rs.close();
            ctx.json(newFolder).status(200);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }

    public static void getOneFolder(Context ctx) {
        UUID folderId = UUID.fromString(ctx.pathParam("folderId"));
        try (
                Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("""
                SELECT f.id AS id, f.name AS name,
                json_agg(json_build_object(
                    'name': t.name,
                    'id': t.id
                )) AS children
                FROM folder f
                JOIN task t ON f.id = t.parent_id
                GROUP BY t.id;
            """)
        ) {
            st.setObject(1, folderId);
            st.setObject(2, folderId);
            ResultSet rs = st.executeQuery();

            // ObjectMapp

            while (rs.next()) {
                String name = rs.getString("name");
                UUID id = (UUID) rs.getObject("id");
                Folder folder = new Folder(name, id, null, null);
            }


            rs.close();
            // ctx.json(folder).status(200);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }
    
    public static void getFolders(Context ctx) {
        try(
            Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("SELECT id, name FROM folder WHERE parent = NULL")
        ) {
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
