package com.karolrinc.books.api.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

    String getUsernameFromToken(@NonNull final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    <T> T getClaimFromToken(@NonNull final String token, @NonNull final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

    Date getExpirationDateFromToken(@NonNull final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    String generateToken(@NonNull final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(@NonNull final Map<String, Object> claims, @NonNull final String subject) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    boolean validateToken(@NonNull final String token, @NonNull final UserDetails userDetails) {
        return isUsernameEqual(token, userDetails) && !isTokenExpired(token);
    }

    private boolean isUsernameEqual(final String token, final UserDetails userDetails) {
        return Objects.equals(getUsernameFromToken(token), userDetails.getUsername());
    }

    private boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
