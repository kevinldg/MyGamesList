package com.github.kevinldg.mygameslist.backend.auth;

public class InputValidator {

    private InputValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateUsername(String username) {
        if (username.length() < 5 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be at least 5 and maximum 20 characters long.");
        }
        if (username.startsWith(" ")) {
            throw new IllegalArgumentException("The user name must not begin with a space.");
        }
        if (username.endsWith(" ")) {
            throw new IllegalArgumentException("The user name must not end with a space.");
        }
        if (username.matches(".*\\s\\s+.*")) {
            throw new IllegalArgumentException("The user name must not contain consecutive spaces.");
        }
    }

    public static void validatePassword(String password, String repeatPassword) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-={}\\[\\];':\"\\\\|,.<>/?].*");
        if (!hasNumber || !hasSpecialChar) {
            throw new IllegalArgumentException("The password must contain at least one number and one special character. Allowed special characters: !@#$%^&*()_+-={}[];:\"\\|,.<>/?");
        }
        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("The passwords do not match.");
        }
    }
}
