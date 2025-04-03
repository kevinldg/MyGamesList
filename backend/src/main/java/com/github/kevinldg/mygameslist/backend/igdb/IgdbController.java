package com.github.kevinldg.mygameslist.backend.igdb;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/igdb")
public class IgdbController {
    private final IgdbService igdbService;

    @GetMapping("/game-and-artwork")
    public ResponseEntity<IgdbGameAndArtwork> searchGameAndArtwork(@RequestParam String name) {
        IgdbGameAndArtwork result = igdbService.searchGameAndArtworkByName(name);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}
