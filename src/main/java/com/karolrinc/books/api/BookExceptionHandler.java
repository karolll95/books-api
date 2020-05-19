package com.karolrinc.books.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
class BookExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest()
                             .body("Entity with given id was not found.");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Internal server error. Please contact support.");
    }
}
