package org.example.response;

import java.util.Map;

public class ErrorResponse {
    String message;
    int status;
    Map<String, String> data;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, Map<String, String> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
