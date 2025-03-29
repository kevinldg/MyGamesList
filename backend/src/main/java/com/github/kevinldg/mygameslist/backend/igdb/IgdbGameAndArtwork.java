package com.github.kevinldg.mygameslist.backend.igdb;

public record IgdbGameAndArtwork(
        int gameId,
        String gameName,
        String gameSummary,
        int artworkId,
        String artworkUrl
) {}
