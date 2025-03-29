package com.github.kevinldg.mygameslist.backend.igdb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IgdbUnitTest {

    @Mock
    private IgdbService igdbService;

    @InjectMocks
    private IgdbController igdbController;

    @Test
    void searchGame_shouldReturnGame() {
        IgdbGame expectedGame = new IgdbGame(1020, List.of(2631), "Grand Theft Auto V", "Game summary");
        when(igdbService.searchGameByName("GTA V")).thenReturn(expectedGame);

        IgdbGame result = igdbController.searchGame("GTA V");

        assertThat(result).isEqualTo(expectedGame);
    }

    @Test
    void searchArtwork_shouldReturnArtwork() {
        IgdbArtwork expectedArtwork = new IgdbArtwork(2636, 1020, "artwork-url");
        when(igdbService.searchArtworkById("1020")).thenReturn(expectedArtwork);

        IgdbArtwork result = igdbController.searchArtwork("1020");

        assertThat(result).isEqualTo(expectedArtwork);
    }

    @Test
    void searchGameAndArtwork_shouldReturnCombinedData() {
        IgdbGameAndArtwork expected = new IgdbGameAndArtwork(
                1020,
                "Grand Theft Auto V",
                "Game summary",
                2636,
                "artwork-url"
        );
        when(igdbService.searchGameAndArtworkByName("GTA V")).thenReturn(expected);

        IgdbGameAndArtwork result = igdbController.searchGameAndArtwork("GTA V");

        assertThat(result).isEqualTo(expected);
    }
}