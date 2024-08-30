package org.example.functional;

import org.example.App;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

public class FolderControllerTest {
    Javalin app = Javalin.create(App::config);

    @Test
    @DisplayName("getFolder endpoint returns all available root folders if the folderId is not provided")
    public void GET_getFolder_without_folderId() {
        JavalinTest.test(app, (server, client) -> {
            // assertEquals(client.get("/folders").code(), 200);
            assertThat(client.get("/folders").code()).isEqualTo(200);
        });
    }
}
