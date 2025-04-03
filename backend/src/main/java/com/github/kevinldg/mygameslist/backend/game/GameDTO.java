package com.github.kevinldg.mygameslist.backend.game;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public record GameDTO(
        String gameName,
        @JsonDeserialize(using = GameStateDeserializer.class)
        GameState gameState
) {}
