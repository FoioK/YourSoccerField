package com.pk.ysf.apimodels.exception;

import org.springframework.http.HttpStatus;

public class BookingException extends AppException {

    public BookingException(String message, ErrorCode error) {
        super(message, HttpStatus.CONFLICT, error);
    }
}
