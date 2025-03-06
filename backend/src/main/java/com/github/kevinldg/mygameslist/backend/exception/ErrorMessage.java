package com.github.kevinldg.mygameslist.backend.exception;

import java.time.Instant;

public record ErrorMessage(String message, Instant timestamp) {}
