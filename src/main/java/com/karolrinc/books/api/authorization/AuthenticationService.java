package com.karolrinc.books.api.authorization;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    void authenticate(@NonNull final String username, @NonNull final String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException(e);
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException(e);
        }
    }
}
