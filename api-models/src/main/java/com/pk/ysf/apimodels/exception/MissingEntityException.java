package com.pk.ysf.apimodels.exception;

import org.springframework.http.HttpStatus;

public class MissingEntityException extends AppException {

    public MissingEntityException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }
}