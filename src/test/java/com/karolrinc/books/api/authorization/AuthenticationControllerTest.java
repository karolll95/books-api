package com.karolrinc.books.api.authorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthenticationControllerTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TOKEN = UUID.randomUUID().toString();

    private final AuthenticationController authenticationController;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private UserDetails userDetails;

    @Autowired
    AuthenticationControllerTest(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    @Test
    void createAuthenticationToken_success() {
        JwtRequest jwtRequest = getJwtRequest();
        when(jwtUserDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(TOKEN);

        ResponseEntity<?> result = authenticationController.createAuthenticationToken(jwtRequest);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody(), new JwtResponse(TOKEN));
        verify(authenticationService).authenticate(USERNAME, PASSWORD);
        verifyNoMoreInteractions(authenticationService);
        verify(jwtUserDetailsService).loadUserByUsername(USERNAME);
        verifyNoMoreInteractions(jwtUserDetailsService);
        verify(jwtTokenUtil).generateToken(userDetails);
        verifyNoMoreInteractions(jwtTokenUtil);
    }

    private JwtRequest getJwtRequest() {
        return JwtRequest.builder()
                         .username(USERNAME)
                         .password(PASSWORD)
                         .build();
    }
}