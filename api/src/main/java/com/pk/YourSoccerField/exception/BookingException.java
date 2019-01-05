package com.pk.YourSoccerField.exception;

import org.springframework.http.HttpStatus;

public class BookingException extends AppException {

    public BookingException(String message, ErrorCode error) {
        super(message, HttpStatus.CONFLICT, error);
    }
}
