package org.example.functional;

import org.example.App;
import org.example.model.File;
import org.example.model.Folder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

public class FolderControllerTest {
    Javalin app = Javalin.create(App::config);

    @Test
    @DisplayName("getFolder endpoint returns all available root folders if the folderId is not provided")
    public void GET_get_all_the_root_folders() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/folders");
            // assertEquals(client.get("/folders").code(), 200);
            assertThat(response.code()).isEqualTo(200);
            // assertThat(client.get("/folders")).
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
            // var json = mapper.writeValueAsString(folder);

            // final Folder r = mapper.reader(Folder.class);git

            // System.out.println("{}{}{}{}{}{}{}{}{} " + response.body().string());
            // System.out.println("{}{}{}{}{}{}{}---- " + json);
            // assertThat(response.body().string()).isEqualTo(json);
        });
    }
}
