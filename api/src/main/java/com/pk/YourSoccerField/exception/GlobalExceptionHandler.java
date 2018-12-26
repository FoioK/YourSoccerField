package com.pk.YourSoccerField.exception;

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
    public Map handle(
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
    public Map handle(
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
        return new ResponseEntity<>(
                formatException(exception, request),
                exception.getHttpStatus()
        );
    }

    private Map<String, Object> formatException(
            Object message,
            HttpServletRequest request
    ) {
        return new HashMap<String, Object>() {{
            put("timestamp", System.currentTimeMillis());
            put("status", HttpStatus.BAD_REQUEST);
            put("error", ErrorCode.INVALID_INPUT);
            put("message", message);
            put("path", request.getRequestURI());
        }};
    }

    private <E extends AppException> ResponseEntity<?> formatException(
            E exception,
            HttpServletRequest request
    ) {
        exception.setPath(request.getRequestURI());

        return new ResponseEntity<>(
                exception,
                exception.getHttpStatus()
        );
    }
}
