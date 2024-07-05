package org.example.controllers;

import io.javalin.http.Context;

public class HelloWorld {
    public static void hello(Context ctx) {
        ctx.json("{\"message\": \"Hello World\"}").status(200);
    }
}
