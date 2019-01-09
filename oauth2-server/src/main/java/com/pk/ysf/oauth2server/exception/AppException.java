package com.pk.ysf.oauth2server.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final Long timestamp;
    private final HttpStatus httpStatus;
    private final ErrorCode error;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorCode getError() {
        return error;
    }

    public String getPath() {
        return "/oauth/token";
    }
}
