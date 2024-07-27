package org.example.response;

import io.javalin.http.HttpResponseException;

// public class ApiResponse extends HttpResponseException {
// int status;
// String message;

// // public ApiResponse(int status, T data) {
// // this.data = data;
// // this.status = status;
// // }

// // public ApiResponse(int status, T error, String message) {
// // this.status = status;
// // this.error = error;
// // this.message = message;
// // }

// public ApiResponse(int status, String message, String data) {
// super(status, message);
// }

// @Override
// public synchronized Throwable fillInStackTrace() {
// return this;
// }
// }

public record ApiResponse(int status, String data, String message) {
}