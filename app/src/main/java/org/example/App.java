/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

import org.example.controllers.FolderController;
import org.example.controllers.HelloWorld;
import org.example.controllers.TaskController;
// import java.sql.*;

import org.jdbi.v3.core.Jdbi;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        /// use a dbPool here instead in the future
        Jdbi jdbi = Database.getJdbi();
        // new postgresql.
        // var dbPool = new Key<Jdbi>("db"); // update this with poolss

        Javalin.create(config -> {
            config.appData(Database.dbKey(), jdbi);

            config.router.mount(router -> {
            }).apiBuilder(() -> {
                path("/", () -> {
                    get(HelloWorld::hello);
                });
                path("/folders", () -> {
                    get(FolderController::getOneFolder);
                    post(FolderController::createFolder);
                    path("{folderId}", () -> {
                        delete(FolderController::deleteFolder);
                        patch(FolderController::updateFolder);
                    });
                });
                path(Path.Task.TASKS, () -> {
                    get(TaskController::getTasks);
                    post(TaskController::createTask);
                    path("{taskId}", () -> {
                        get(TaskController::getOneTask);
                        patch(TaskController::updateTask);
                        delete(TaskController::deleteTask);
                    });
                });
            });
        }).start(7070);

    }
}
