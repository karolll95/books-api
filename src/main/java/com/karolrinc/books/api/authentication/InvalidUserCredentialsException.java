package com.karolrinc.books.api.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidUserCredentialsException extends RuntimeException {
    private static final long serialVersionUID = 4062756271892324202L;
    public static final String DEFAULT_MSG = "User credentials are invalid.";

    InvalidUserCredentialsException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
