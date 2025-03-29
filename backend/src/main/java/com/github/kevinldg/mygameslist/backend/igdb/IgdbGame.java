package com.github.kevinldg.mygameslist.backend.igdb;

import java.util.List;

public record IgdbGame(
        int id,
        List<Integer> artworks,
        String name,
        String summary
) {}
