package com.github.kevinldg.mygameslist.backend.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtUnitTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String secretKey = "1234567890987654321234567890987654321";
    private final String username = "testuser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "secret", secretKey);
        when(userDetails.getUsername()).thenReturn(username);
    }

    @Test
    void generateTokenShouldCreateValidToken() {
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsernameShouldReturnCorrectUsername() {
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void validateTokenShouldReturnTrueForValidToken() {
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void validateTokenShouldReturnFalseForExpiredToken() {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)) // 2 days ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hour ago
                .signWith(key)
                .compact();

        try {
            boolean isValid = jwtService.validateToken(expiredToken, userDetails);
            assertFalse(isValid);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void validateTokenShouldReturnFalseForInvalidUsername() {
        String token = jwtService.generateToken(username);
        UserDetails otherUser = org.mockito.Mockito.mock(UserDetails.class);
        when(otherUser.getUsername()).thenReturn("otheruser");

        boolean isValid = jwtService.validateToken(token, otherUser);

        assertFalse(isValid);
    }
}