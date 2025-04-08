package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;

import java.time.Instant;
import java.util.List;

public record UserInfoDTO(
        String id,
        String username,
        Instant createdAt,
        List<Game> games,
        Game favoriteGame
) {}
