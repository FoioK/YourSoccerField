package com.pk.ysf.config;

import com.pk.ysf.apimodels.exception.AppException;
import com.pk.ysf.apimodels.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        return formatException(
                exception.getBindingResult().getFieldErrors()
                        .stream()
                        .map(fieldError ->
                                fieldError.getField() +
                                        " " +
                                        fieldError.getDefaultMessage()
                        )
                        .collect(Collectors.toList()),
                request
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handle(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        return formatException(
                exception.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()),
                request
        );
    }

    @ExceptionHandler
    @ResponseBody
    public <E extends AppException> ResponseEntity<?> handle(
            E exception,
            HttpServletRequest request
    ) {
        return formatException(exception, request);
    }

    private ResponseEntity<Map<String, Object>> formatException(
            Object message,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                map(message, request),
                HttpStatus.BAD_REQUEST
        );
    }

    private <E extends AppException> ResponseEntity<?> formatException(
            E exception,
            HttpServletRequest request
    ) {
        exception.setPath(request.getRequestURI());

        return new ResponseEntity<>(
                this.map(exception),
                exception.getHttpStatus()
        );
    }

    private Map<String, Object> map(Object message, HttpServletRequest request) {
        return new HashMap<String, Object>() {{
            put("timestamp", System.currentTimeMillis());
            put("status", HttpStatus.BAD_REQUEST);
            put("error", ErrorCode.INVALID_INPUT);
            put("message", message);
            put("path", request.getRequestURI());
        }};
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
