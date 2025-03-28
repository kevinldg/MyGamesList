package com.github.kevinldg.mygameslist.backend.auth;

import com.github.kevinldg.mygameslist.backend.exception.UserAlreadyExistsException;
import com.github.kevinldg.mygameslist.backend.user.User;
import com.github.kevinldg.mygameslist.backend.user.UserInfoDTO;
import com.github.kevinldg.mygameslist.backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthUnitTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private final String testUsername = "testuser";
    private final String testPassword = "password123";
    private final String encodedPassword = "encodedPassword";
    private final String testToken = "jwtToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.generateToken(anyString())).thenReturn(testToken);
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
    }

    @Test
    void registerShouldCreateNewUserAndReturnToken() {
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(User.builder()
                .username(testUsername)
                .password(encodedPassword)
                .build());

        String result = authService.register(testUsername, testPassword);

        assertEquals(testToken, result);
        verify(userRepository).findByUsername(testUsername);
        verify(passwordEncoder).encode(testPassword);
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(testUsername);
    }

    @Test
    void registerShouldThrowExceptionWhenUserAlreadyExists() {
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(
                User.builder()
                        .username(testUsername)
                        .password(encodedPassword)
                        .build()));

        assertThrows(UserAlreadyExistsException.class, () ->
                authService.register(testUsername, testPassword));

        verify(userRepository).findByUsername(testUsername);
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void loginShouldReturnTokenForValidCredentials() {
        User mockUser = User.builder()
                .username(testUsername)
                .password(encodedPassword)
                .build();

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(testPassword, encodedPassword)).thenReturn(true);

        String result = authService.login(testUsername, testPassword);

        assertEquals(testToken, result);
        verify(userRepository).findByUsername(testUsername);
        verify(passwordEncoder).matches(testPassword, encodedPassword);
        verify(jwtService).generateToken(testUsername);
    }

    @Test
    void loginShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                authService.login(testUsername, testPassword));

        verify(userRepository).findByUsername(testUsername);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void loginShouldThrowExceptionWhenPasswordInvalid() {
        User mockUser = User.builder()
                .username(testUsername)
                .password(encodedPassword)
                .build();

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(testPassword, encodedPassword)).thenReturn(false);

        assertThrows(BadCredentialsException.class, () ->
                authService.login(testUsername, testPassword));

        verify(userRepository).findByUsername(testUsername);
        verify(passwordEncoder).matches(testPassword, encodedPassword);
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void getUserInfoShouldReturnUserInfoForValidUsername() {
        String testId = "testId";
        Instant testCreatedAt = Instant.now();
        User mockUser = User.builder()
                .id(testId)
                .username(testUsername)
                .password(encodedPassword)
                .createdAt(testCreatedAt)
                .build();

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(mockUser));

        UserInfoDTO result = authService.getUserInfo(testUsername);

        assertEquals(testId, result.id());
        assertEquals(testUsername, result.username());
        assertEquals(testCreatedAt, result.createdAt());
        verify(userRepository).findByUsername(testUsername);
    }

    @Test
    void getUserInfoShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                authService.getUserInfo(testUsername));

        verify(userRepository).findByUsername(testUsername);
    }
}