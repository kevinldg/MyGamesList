package com.github.kevinldg.mygameslist.backend.game;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.kevinldg.mygameslist.backend.exception.InvalidGameStateException;

import java.io.IOException;

public class GameStateDeserializer extends JsonDeserializer<GameState> {
    @Override
    public GameState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        for (GameState state : GameState.values()) {
            if (state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new InvalidGameStateException(value);
    }
}