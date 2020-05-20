package com.karolrinc.books.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookExceptionHandlerTest {

    private final BookExceptionHandler bookExceptionHandler;

    @Autowired
    BookExceptionHandlerTest(BookExceptionHandler bookExceptionHandler) {this.bookExceptionHandler = bookExceptionHandler;}

    @Test
    void handleEntityNotFoundException_success() {
        ResponseEntity<?> result = bookExceptionHandler.handleEntityNotFoundException(new EntityNotFoundException());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Entity with given id was not found.", result.getBody());
    }

    @Test
    void handleRuntimeException_success() {
        ResponseEntity<?> result = bookExceptionHandler.handleRuntimeException(new RuntimeException());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Internal server error. Please contact support.", result.getBody());
    }
}