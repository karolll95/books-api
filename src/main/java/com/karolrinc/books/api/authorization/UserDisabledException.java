package com.karolrinc.books.api.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserDisabledException extends RuntimeException {
    private static final long serialVersionUID = 7425566701776209605L;
    public static final String DEFAULT_MSG = "Requested user is disabled.";

    UserDisabledException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
