package com.github.kevinldg.mygameslist.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class AuthUnitTest {
    private JwtService jwtService;
    private final String jwtSecret = "12345678901234567890123456789012";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", jwtSecret);
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";

        String token = jwtService.generateToken(username);

        assertThat(token)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void testGeneratedTokenContainsUsername() {
        String username = "testuser";

        String token = jwtService.generateToken(username);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject()).isEqualTo(username);
    }

    @Test
    void testGeneratedTokenHasExpiration() {
        String username = "testuser";

        String token = jwtService.generateToken(username);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();
        assertThat(expirationDate)
                .isNotNull()
                .isAfter(new Date());

        long expectedExpiration = System.currentTimeMillis() + 1000 * 60 * 60 * 24 - 5000;
        assertThat(expirationDate.getTime()).isCloseTo(expectedExpiration, within(5000L));
    }
}
