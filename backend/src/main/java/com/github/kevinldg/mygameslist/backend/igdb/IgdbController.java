package com.github.kevinldg.mygameslist.backend.igdb;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/igdb")
public class IgdbController {
    private final IgdbService igdbService;

    @GetMapping("/games")
    public IgdbGame searchGame(@RequestParam String name) {
        return igdbService.searchGameByName(name);
    }

    @GetMapping("/artworks")
    public IgdbArtwork searchArtwork(@RequestParam String id) {
        return igdbService.searchArtworkById(id);
    }

    @GetMapping("/game-and-artwork")
    public IgdbGameAndArtwork searchGameAndArtwork(@RequestParam String name) {
        return igdbService.searchGameAndArtworkByName(name);
    }
}
