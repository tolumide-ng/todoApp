package org.example.controllers;

import io.javalin.http.Context;
import java.util.UUID;

import org.example.Database;
import org.example.dao.FileDao;
import org.example.model.File;
import org.example.response.ApiResponse;
import org.example.response.ErrorResponse;
import org.jdbi.v3.core.Jdbi;

public class FileController {
    // public record File(String name, UUID id, UUID parent, String extension,
    // DataType type) {
    // };

    public record CreateFile(UUID parent, String name) {
    }

    public static void createFile(Context ctx) {
        try {
            Jdbi dbPool = ctx.appData(Database.dbKey());
            CreateFile data = ctx.bodyAsClass(CreateFile.class);

            File file = dbPool.withHandle(handle -> {
                FileDao dao = handle.attach(FileDao.class);
                return dao.createFile(data.parent, data.name);
            });

            if (file == null) {
                ctx.json("Please confirm that the file name is unique in the provided parent");
                return;
            }
            ctx.json(file);
        } catch (Exception e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", "Internal Server Error"));
        }

        return;
    }

    public static void getOneFile(Context ctx) {
        try {
            String id = ctx.queryParam("fileId");
            UUID fileId = UUID.fromString(id);

            Jdbi dbPool = ctx.appData(Database.dbKey());
            File file = dbPool.withHandle(handle -> {
                FileDao dao = handle.attach(FileDao.class);
                return dao.getFile(fileId);
            });

            if (file == null) {
                ctx.json(new ErrorResponse(404, "Not Found", "File not found"));
                return;
            }

            ctx.json(file);
        } catch (IllegalArgumentException e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", null));
        }
        return;
    }

    public static void updateFile(Context ctx) {
        try {
            UUID fileId = UUID.fromString(ctx.queryParam("fileId"));
            UUID parentId = UUID.fromString(ctx.queryParam("parentId"));

            String name = ctx.queryParam("name");

            Jdbi dbPool = ctx.appData(Database.dbKey());
            File file = dbPool.withHandle(handle -> {
                FileDao dao = handle.attach(FileDao.class);
                return dao.updateFile(name, fileId, parentId);
            });

            if (file == null) {
                ctx.json(file);
            }
        } catch (Exception e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", null));
        }

        return;
    }

    public static void deleteFile(Context ctx) {
        try {
            UUID id = UUID.fromString(ctx.pathParam("fileId"));
            Jdbi dbPool = ctx.appData(Database.dbKey());
            dbPool.withHandle(handle -> {
                FileDao dao = handle.attach(FileDao.class);
                dao.deleteFile(id);
                return null;
            });

            ctx.json(new ApiResponse(202, "Success", "Ok")).status(202);
        } catch (IllegalArgumentException e) {
            ctx.json(new ErrorResponse(500, "Internal Server Error", null));
        }
        return;
    }
}
