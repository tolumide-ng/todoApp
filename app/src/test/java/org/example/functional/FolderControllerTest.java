package org.example.functional;

import org.example.App;
import org.example.controllers.FolderController.CreateFolder;
import org.example.model.File;
import org.example.model.Folder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import java.util.regex.Pattern;

public class FolderControllerTest {
    Javalin app = Javalin.create(App::config);

    @Test
    @DisplayName("getFolder endpoint returns all available root folders if the folderId is not provided")
    public void GET_get_all_the_root_folders() {
        /// It's time to use a testDb here, with expected data been provided at
        /// begining, and cleaned up aferEach
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/folders");
            assertThat(response.code()).isEqualTo(200);

            Folder[] emptyFolder = {};
            File[] emptyFile = {};
            Folder countries = new Folder("-ountries", UUID.fromString("2d7132ca-0d2c-4912-8583-e24c15d0ffc9"),
                    emptyFolder,
                    emptyFile);
            Folder rides = new Folder("-rides & black\"", UUID.fromString("631234b8-5307-4411-b298-ec873cfd0626"),
                    emptyFolder,
                    emptyFile);

            Folder[] folders = { countries, rides };
            Folder folder = new Folder("", null, folders, emptyFile);

            // folder.toString().toString());
            var mapper = new ObjectMapper();
            var json = mapper.writeValueAsString(folder);

            assertThat(response.body().string()).isEqualTo(json);
        });
    }

    @Test
    @DisplayName("Should create a Folder at the root level")
    public void POST_create_folder_at_root_level() {
        JavalinTest.test(app, (server, client) -> {
            var mapper = new ObjectMapper();

            // String name = "test__" + String.valueOf(Math.random());
            String random = String.valueOf(Math.random());
            String name = "test__" + Pattern.compile(".").split(random, 5)[4];

            UUID owner = UUID.fromString("a59f80e5-e568-491d-b8bc-94b979f3c61a");

            CreateFolder folder = new CreateFolder(null, name, owner);
            var payload = mapper.writeValueAsString(folder);

            System.out.println(">>>>>>>>>>>>>>>>>>>>|||||||||||||" + payload);

            var response = client.post("/folders", payload);
            System.out.println("{}{}{}{}{}{}{}{}{} " + response.body().string());
            assertThat(response.code()).isEqualTo(201);

            // assertThat(false).isTrue();

        });
    }
}
