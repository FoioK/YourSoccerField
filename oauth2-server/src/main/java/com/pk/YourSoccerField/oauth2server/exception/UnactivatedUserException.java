package com.pk.YourSoccerField.oauth2server.exception;

import org.springframework.http.HttpStatus;

public class UnactivatedUserException extends AppException {

    public UnactivatedUserException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.UNAUTHORIZED, errorCode);
    }
}
