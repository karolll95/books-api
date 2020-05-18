package com.karolrinc.books.api.authentication;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
class JwtRequest implements Serializable {
    private static final long serialVersionUID = -5523831512846645427L;

    private String username;
    private String password;
}
