package org.example.controllers;

import io.javalin.http.Context;
import java.util.UUID;

import org.example.Database;
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

            // T
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void getOneFile(Context ctx) {
    }

    public static void getFiles(Context ctx) {
    }

    public static void updateFile(Context ctx) {
    }

    public static void deleteFile(Context ctx) {
    }

    // public static File File(String string, UUID randomUUID) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'File'");
    // }

}
