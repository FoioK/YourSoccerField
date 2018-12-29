package com.pk.YourSoccerField.oauth2server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
                this.map(exception),
                exception.getHttpStatus()
        );
    }

    @SuppressWarnings("Duplicates")
    private <E extends AppException> Map<String, Object> map(E exception) {
        return new HashMap<String, Object>() {{
            put("timestamp", exception.getTimestamp());
            put("status", exception.getHttpStatus());
            put("error", exception.getError());
            put("message", exception.getMessage());
            put("path", exception.getPath());
        }};
    }
}
