package com.pk.ysf.oauth2server.exception;

import org.springframework.http.HttpStatus;

public class MissingEntityException extends AppException {

    public MissingEntityException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }
}
