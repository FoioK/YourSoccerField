package com.pk.YourSoccerField.exception;

import org.springframework.http.HttpStatus;

public class DuplicatEntityException extends AppException {

    public DuplicatEntityException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.CONFLICT, errorCode);
    }
}