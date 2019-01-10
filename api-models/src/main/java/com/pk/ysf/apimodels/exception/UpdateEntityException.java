package com.pk.ysf.apimodels.exception;

import org.springframework.http.HttpStatus;

public class UpdateEntityException extends AppException {

    public UpdateEntityException(String message, ErrorCode errorCode) {
        super(message, HttpStatus.INSUFFICIENT_STORAGE, errorCode);
    }
}
