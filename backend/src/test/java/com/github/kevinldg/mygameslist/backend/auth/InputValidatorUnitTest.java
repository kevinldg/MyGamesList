package com.github.kevinldg.mygameslist.backend.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorUnitTest {

    @Test
    void testValidUsername() {
        assertDoesNotThrow(() -> InputValidator.validateUsername("validUser"));
    }

    @Test
    void testUsernameTooShort() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateUsername("usr"));
        assertTrue(ex.getMessage().contains("at least 5"));
    }

    @Test
    void testUsernameTooLong() {
        String longUsername = "thisIsAUsernameTooLong";
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateUsername(longUsername));
        assertTrue(ex.getMessage().contains("maximum 20"));
    }

    @Test
    void testUsernameLeadingSpace() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateUsername(" leadingUser"));
        assertTrue(ex.getMessage().contains("not begin with a space"));
    }

    @Test
    void testUsernameTrailingSpace() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateUsername("trailingUser "));
        assertTrue(ex.getMessage().contains("not end with a space"));
    }

    @Test
    void testUsernameConsecutiveSpaces() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateUsername("user  name"));
        assertTrue(ex.getMessage().contains("consecutive spaces"));
    }

    @Test
    void testValidPassword() {
        assertDoesNotThrow(() -> InputValidator.validatePassword("Password1!", "Password1!"));
    }

    @Test
    void testPasswordTooShort() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePassword("Pw1!", "Pw1!"));
        assertTrue(ex.getMessage().contains("at least 8"));
    }

    @Test
    void testPasswordMissingNumber() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePassword("Password!", "Password!"));
        assertTrue(ex.getMessage().contains("one number"));
    }

    @Test
    void testPasswordMissingSpecialChar() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePassword("Password1", "Password1"));
        assertTrue(ex.getMessage().contains("one special character"));
    }

    @Test
    void testPasswordsDoNotMatch() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validatePassword("Password1!", "Password2!"));
        assertTrue(ex.getMessage().contains("do not match"));
    }
}