package com.karolrinc.books.api.authentication;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.String.format;

@Service
class JwtUserDetailsService implements UserDetailsService {
    private static final String USERNAME_NOT_FOUND_MSG = "User with username: %s not found.";

    @Value("${auth.username}")
    private String username;

    @Value("${auth.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(@NonNull final String username) {
        if (Objects.equals(this.username, username)) {
            return new User(this.username, this.password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(format(USERNAME_NOT_FOUND_MSG, username));
        }
    }
}
