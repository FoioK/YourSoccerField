package com.pk.ysf.apimodels.exception;

import org.springframework.http.HttpStatus;

public class CreateEntityException extends AppException {

    public CreateEntityException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.INSUFFICIENT_STORAGE, errorCode);
    }
}
