package com.karolrinc.books.api.authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationServiceTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final AuthenticationService authenticationService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationServiceTest(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Test
    void authenticate_success() {
        authenticationService.authenticate(USERNAME, PASSWORD);

        verify(authenticationManager).authenticate(any(Authentication.class));
        verifyNoMoreInteractions(authenticationManager);
    }

    @Test
    void authenticate_userDisabled() {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new DisabledException(""));

        UserDisabledException exception =
                assertThrows(UserDisabledException.class, () -> authenticationService.authenticate(USERNAME, PASSWORD));

        assertTrue(exception.getMessage().contains("Requested user is disabled."));
        verify(authenticationManager).authenticate(any(Authentication.class));
        verifyNoMoreInteractions(authenticationManager);
    }

    @Test
    void authenticate_invalidCredentials() {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException(""));

        InvalidUserCredentialsException exception =
                assertThrows(InvalidUserCredentialsException.class, () -> authenticationService.authenticate(USERNAME, PASSWORD));

        assertTrue(exception.getMessage().contains("User credentials are invalid."));
        verify(authenticationManager).authenticate(any(Authentication.class));
        verifyNoMoreInteractions(authenticationManager);
    }
}