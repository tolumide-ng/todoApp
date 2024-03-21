package org.example.controllers;

import io.javalin.config.Key;
import io.javalin.http.Context;
import java.util.UUID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.example.DB;


public class FolderController {
    public record Folder(String name, UUID id) {}

    public static void createFolder(Context ctx) {
        Folder folder = ctx.bodyValidator(Folder.class)
                .check(data -> data.name != null && !data.name.isEmpty(), "Please provide a valid Folder name").get();

        try (
            Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("INSERT INTO folder (name) VALUES(?) RETURNING *")
        ) {
            st.setString(1, folder.name);
            ResultSet rs = st.executeQuery();
            rs.next();

            String name = rs.getString("name");
            UUID id = (UUID) rs.getObject("id");

            folder = new Folder(name, id);

            rs.close();
            ctx.json(folder).status(200);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }

    public static void getOneFolder(Context ctx) {
        UUID folderId = UUID.fromString(ctx.pathParam("folderId"));
        try (
            Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("SELECT id, name FROM folder WHERE id = ?")
        ) {
            st.setObject(1, folderId);
            ResultSet rs = st.executeQuery();
            rs.next();


            String name = rs.getString("name");
            UUID id = (UUID) rs.getObject("id");
            Folder folder = new Folder(name, id);

            rs.close();
            ctx.json(folder).status(200);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.json("Internal Server Error").status(500);
        }
    }
    
    public static void getFolders(Context ctx) {
    }

    public static void updateFolder(Context ctx) {
    }
    
    public static void deleteFolder(Context ctx) {}

}
