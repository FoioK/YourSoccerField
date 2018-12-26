package com.pk.YourSoccerField.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final Long timestamp;
    private final HttpStatus httpStatus;
    private final ErrorCode error;
    private String path = "";

    public AppException(
            String message,
            HttpStatus httpStatus,
            ErrorCode error
    ) {
        super(message);
        this.timestamp = System.currentTimeMillis();
        this.httpStatus = httpStatus;
        this.error = error;
    }

    Long getTimestamp() {
        return timestamp;
    }

    HttpStatus getHttpStatus() {
        return httpStatus;
    }

    ErrorCode getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
