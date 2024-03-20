package org.example.controllers;

import io.javalin.config.Key;
import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
// org.postgresql:postgresql
import java.sql.PreparedStatement;

public class FolderController {
    public record FolderInit(String name) {}
    public record Folder(String name, String id) {}
    
    
    static final Key<Connection> dbPool = new Key<>("pool");

    public static void createFolder(Context ctx) {
        FolderInit folder = ctx.bodyAsClass(FolderInit.class);
        ResultSet rs = null;
        
        try (PreparedStatement st = ctx.appData(dbPool).prepareStatement("INSERT INTO folder (name) VALUE(?) RETURNING *")) {
            st.setString(1, folder.name);
            rs = st.executeQuery();

            while (rs.next()) {
                System.out.println("Column 1 returned");
                System.out.println(rs.getClass());
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
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
