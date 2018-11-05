package com.pk.YourSoccerField.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public AppException(String message, HttpStatus httpStatus, ErrorCode errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
