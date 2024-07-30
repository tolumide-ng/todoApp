package org.example.controllers;

import java.util.UUID;

import org.example.Database;
import org.example.dao.FolderDao;
import org.example.model.Folder;
import org.example.response.ErrorResponse;
import org.jdbi.v3.core.Jdbi;

import io.javalin.http.Context;
;

public class FolderController {

    public record CreateFolder(UUID parent, String name, UUID owner){
        // public CreateFolder {
        //     this.name = Objects.requireNonNull(name, "");
        // }
    }

    public static void createFolder(Context ctx) {
        try {
            Jdbi dbPool = ctx.appData(Database.dbKey());
            CreateFolder data = ctx.bodyAsClass(CreateFolder.class);
    
            Folder folder = dbPool.withHandle(handle -> {
                FolderDao dao = handle.attach(FolderDao.class);
                return dao.createFolder(data.parent, data.name, data.owner);
            });
    
            if (folder == null) {
                ctx.json("Please confirm that the folder name is unique");
                return;
            }
    
            ctx.json(new Folder(folder.name(), folder.id(), null, null));    
        } catch (Exception e) {
            ctx.json(new ErrorResponse(400, "Bad Request", "Please confirm the name is unique, and all required data are provided"));
        }
    }

    public static void getOneFolder(Context ctx) {
        try {
            UUID folderId = UUID.fromString(ctx.pathParam("folderId"));
        
            Jdbi dbPool = ctx.appData(Database.dbKey());
            Folder folder = dbPool.withHandle(handle -> {
                FolderDao dao = handle.attach(FolderDao.class);
                return dao.getOneFolder(folderId);
            });
        
            if (folder == null) {
                ctx.json(new ErrorResponse(404, "Not Found", "Folder not found"));
                return;
            }
        
            ctx.json(folder).status(200);

        } catch (IllegalArgumentException e) {
            ctx.json(new ErrorResponse(400, "Bad Request", "Please provide a valid folder UUID"));
        }


    }

    public static void getFolders(Context ctx) {
        System.out.println("getFoldersgetFoldersgetFoldersgetFolders--getFoldersgetFolders>>>>>");

        Jdbi dbPool = ctx.appData(Database.dbKey());
        // Folder[] folder = dbPool.withHandle(handle -> {
        //     FolderDao dao = handle.attach(FolderDao.class);
        //     // return dao.
        // });
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
