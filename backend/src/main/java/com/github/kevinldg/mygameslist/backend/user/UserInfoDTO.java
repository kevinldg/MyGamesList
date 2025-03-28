package com.github.kevinldg.mygameslist.backend.user;

import java.time.Instant;

public record UserInfoDTO(
        String id,
        String username,
        Instant createdAt
) {}
