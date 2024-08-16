package org.example.controllers;

import java.util.UUID;

import org.example.Database;
import org.example.dao.FolderDao;
import org.example.model.Folder;
import org.example.response.ApiResponse;
import org.example.response.ErrorResponse;
import org.jdbi.v3.core.Jdbi;

import io.javalin.http.Context;;

public class FolderController {

    public record CreateFolder(UUID parent, String name, UUID owner) {
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
            return;
        } catch (Exception e) {
            ctx.json(new ErrorResponse(400, "Bad Request",
                    "Please confirm the name is unique, and all required data are provided"));
        }
    }

    public static void getFolder(Context ctx) {
        try {
            // UUID folderId = UUID.fromString(ctx.pathParam("folderId"));
            String id = ctx.queryParam("folderId");
            UUID folderId = id == null ? null : UUID.fromString(id);

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
        return;
    }

    public static void updateFolder(Context ctx) {
        try {
            String id = ctx.queryParam("folderId");
            UUID folderId = UUID.fromString(id);

            Jdbi dbPool = ctx.appData(Database.dbKey());
            CreateFolder data = ctx.bodyAsClass(CreateFolder.class);
            Folder folder = dbPool.withHandle(handle -> {
                FolderDao dao = handle.attach(FolderDao.class);
                return dao.updateFolder(data.parent, data.name, folderId);
            });

            if (folder == null) {
                ctx.json(new ErrorResponse(404, "Not Found", "folder not found"));
            }
        } catch (IllegalArgumentException e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", null));
        }
        return;
    }

    public static void deleteFolder(Context ctx) {
        try {
            UUID folderId = UUID.fromString(ctx.pathParam("folderId"));

            Jdbi dbPool = ctx.appData(Database.dbKey());

            dbPool.withHandle(handle -> {
                FolderDao dao = handle.attach(FolderDao.class);
                dao.deleteFolder(folderId);
                return null;
            });

            ctx.json(new ApiResponse(202, "Success", "Ok")).status(202);
            return;
        } catch (Exception e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", "Internal Server Error"));
        }
    }

}
