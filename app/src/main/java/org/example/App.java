/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import org.example.controllers.FolderController;
import org.example.controllers.TaskController;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }


    public static void main(String[] args) {

        Javalin.create(config -> {
            // config.appData(dbPool, connection);
            config.router.mount(router -> {}).apiBuilder(() -> {
                path(Path.Folder.FOLDERS, () -> {
                    get(FolderController::getFolders);
                    post(FolderController::createFolder);
                    path("{folderId}", () -> {
                        get(FolderController::getOneFolder);
                        patch(FolderController::updateFolder);
                        delete(FolderController::deleteFolder);
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
