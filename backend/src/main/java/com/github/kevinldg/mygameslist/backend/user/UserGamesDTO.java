package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;

import java.util.List;

public record UserGamesDTO(List<Game> games) {}
