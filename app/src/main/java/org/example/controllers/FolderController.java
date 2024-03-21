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
    // public record FolderInit(String name) {}
    public record Folder(String name, UUID id) {}
    
    
    static final Key<Connection> dbPool = new Key<>("pool");

    public static void createFolder(Context ctx) {
        
        Folder folder = ctx.bodyValidator(Folder.class)
                .check(data -> data.name != null && !data.name.isEmpty(), "Please provide a valid Folder name").get();
        ResultSet rs = null;


        try (
            Connection conn = DB.connect();
            PreparedStatement st = conn.prepareStatement("INSERT INTO folder (name) VALUES(?) RETURNING *")
        ) {
            st.setString(1, folder.name);
            rs = st.executeQuery();
            rs.next();

            String name = rs.getString("name");
            UUID id = (UUID) rs.getObject("id");

            folder = new Folder(name, id);

            rs.close();
            ctx.json(folder).status(200);
        } catch (SQLException e) {
            System.out.println("||||||||||||||==========|||||||||||||||||||||||||||==========|||||||||||||");
            e.printStackTrace();
        }
    }

    public static void getOneFolder(Context ctx) {
    }
    
    public static void getFolders(Context ctx) {
    }

    public static void updateFolder(Context ctx) {
    }
    
    public static void deleteFolder(Context ctx) {}

}
