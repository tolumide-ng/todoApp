package org.example.controllers;

import io.javalin.http.Context;
import java.util.UUID;

public class TaskController {
    public record Task(String name, UUID id, UUID parent_id) {};

    public static void createTask(Context ctx) {
    }

    public static void getOneTask(Context ctx) {
    }

    public static void getTasks(Context ctx) {
    }

    public static void updateTask(Context ctx) {
    }

    public static void deleteTask(Context ctx) {
    }

    public static Task Task(String string, UUID randomUUID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Task'");
    }
    
}
