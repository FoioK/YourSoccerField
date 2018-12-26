package com.pk.YourSoccerField.oauth2server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public <E extends AppException> ResponseEntity<?> handle(E exception) {
        return new ResponseEntity<>(
                formatException(exception),
                exception.getHttpStatus()
        );
    }

    private <E extends AppException> ResponseEntity<?> formatException(E exception) {
        return new ResponseEntity<>(
                exception,
                exception.getHttpStatus()
        );
    }
}
