package com.karolrinc.books.api.authentication;

import lombok.Data;

import java.io.Serializable;

@Data
class JwtResponse implements Serializable {
    private static final long serialVersionUID = -7139900908662285133L;

    private final String jwtToken;

}
