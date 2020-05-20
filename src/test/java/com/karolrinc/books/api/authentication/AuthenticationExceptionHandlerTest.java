package com.karolrinc.books.api.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuthenticationExceptionHandlerTest {

    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    @Autowired
    AuthenticationExceptionHandlerTest(AuthenticationExceptionHandler authenticationExceptionHandler) {
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Test
    void handleUserDisabledException_success() {
        ResponseEntity<?> result = authenticationExceptionHandler.handleUserDisabledException(
                new UserDisabledException(new RuntimeException()));

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(UserDisabledException.DEFAULT_MSG, result.getBody());
    }

    @Test
    void handleInvalidUserCredentialsException_success() {
        ResponseEntity<?> result = authenticationExceptionHandler.handleInvalidUserCredentialsException(
                new InvalidUserCredentialsException(new RuntimeException()));

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(InvalidUserCredentialsException.DEFAULT_MSG, result.getBody());
    }

    @Test
    void handleRuntimeException() {
        ResponseEntity<?> result = authenticationExceptionHandler.handleRuntimeException(new RuntimeException());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Something went wrong. Please contact support.", result.getBody());
    }
}