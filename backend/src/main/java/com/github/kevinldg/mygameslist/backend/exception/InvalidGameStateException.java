package com.github.kevinldg.mygameslist.backend.exception;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException(String state) {
        super("Invalid game state: " + state);
    }
}
