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
                error(exception.getMessage(), exception.getErrorCode()),
                exception.getHttpStatus()
        );
    }

    private Map error(Object message, ErrorCode errorCode) {
        return new HashMap<String, Object>() {{
            put("message", message);
            put("errorCode", errorCode);
        }};
    }
}
