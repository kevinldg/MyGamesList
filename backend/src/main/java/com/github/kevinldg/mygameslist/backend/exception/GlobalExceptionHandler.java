package com.github.kevinldg.mygameslist.backend.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleGlobalException(Exception exception) {
        return new ErrorMessage(Instant.now(), exception.getMessage());
    }
}
