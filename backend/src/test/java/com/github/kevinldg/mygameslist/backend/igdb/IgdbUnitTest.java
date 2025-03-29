package com.github.kevinldg.mygameslist.backend.igdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinldg.mygameslist.backend.exception.IgdbException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IgdbUnitTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private IgdbService igdbService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();

        when(restClientBuilder.baseUrl(anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);

        igdbService = new IgdbService(restClientBuilder, objectMapper, "testId", "testToken");
    }

    @Test
    void searchGameByName_shouldReturnGame_whenValidResponseReceived() {
        String gameResponse = """
            [{
                "id": 1020,
                "artworks": [2631],
                "name": "Grand Theft Auto V",
                "summary": "Game summary"
            }]
            """;
        when(responseSpec.body(String.class)).thenReturn(gameResponse);

        IgdbGame result = igdbService.searchGameByName("GTA V");

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1020);
        assertThat(result.artworks()).containsExactly(2631);
        assertThat(result.name()).isEqualTo("Grand Theft Auto V");
        assertThat(result.summary()).isEqualTo("Game summary");
    }

    @Test
    void searchArtwork_shouldReturnArtwork_whenValidResponseReceived() {
        String artworkResponse = """
            [{
                "id": 2636,
                "game": 1020,
                "url": "artwork-url"
            }]
            """;
        when(responseSpec.body(String.class)).thenReturn(artworkResponse);

        IgdbArtwork result = igdbService.searchArtworkById("1020");

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(2636);
        assertThat(result.game()).isEqualTo(1020);
        assertThat(result.url()).isEqualTo("artwork-url");
    }

    @Test
    void searchGameAndArtwork_shouldReturnCombinedData() {
        String gameResponse = """
            [{
                "id": 1020,
                "artworks": [2631],
                "name": "Grand Theft Auto V",
                "summary": "Game summary"
            }]
            """;
        String artworkResponse = """
            [{
                "id": 2636,
                "game": 1020,
                "url": "artwork-url"
            }]
            """;

        when(responseSpec.body(String.class))
                .thenReturn(gameResponse)
                .thenReturn(artworkResponse);

        IgdbGameAndArtwork result = igdbService.searchGameAndArtworkByName("GTA V");

        assertThat(result).isNotNull();
        assertThat(result.gameId()).isEqualTo(1020);
        assertThat(result.gameName()).isEqualTo("Grand Theft Auto V");
        assertThat(result.gameSummary()).isEqualTo("Game summary");
        assertThat(result.artworkId()).isEqualTo(2636);
        assertThat(result.artworkUrl()).isEqualTo("artwork-url");
    }

    @Test
    void shouldThrowIgdbException_whenJsonProcessingFails() {
        when(responseSpec.body(String.class)).thenReturn("invalid json");

        assertThrows(IgdbException.class, () ->
                igdbService.searchGameByName("GTA V")
        );
    }

    @Test
    void searchGameByName_shouldReturnNull_whenNoGameWithRequiredFieldsFound() {
        String gameResponse = """
        [{
            "id": 1020,
            "artworks": [2631],
            "name": "Grand Theft Auto V"
        }]
        """;
        when(responseSpec.body(String.class)).thenReturn(gameResponse);

        IgdbGame result = igdbService.searchGameByName("GTA V");

        assertThat(result).isNull();
    }

    @Test
    void searchArtworkById_shouldReturnNull_whenNoArtworkFound() {
        String artworkResponse = "[]";
        when(responseSpec.body(String.class)).thenReturn(artworkResponse);

        IgdbArtwork result = igdbService.searchArtworkById("1020");

        assertThat(result).isNull();
    }

    @Test
    void searchGameAndArtwork_shouldReturnNull_whenNoGameFound() {
        when(responseSpec.body(String.class)).thenReturn("[]");

        IgdbGameAndArtwork result = igdbService.searchGameAndArtworkByName("NonExistentGame");

        assertThat(result).isNull();
    }

    @Test
    void searchGameAndArtwork_shouldReturnNull_whenNoArtworkFound() {
        String gameResponse = """
        [{
            "id": 1020,
            "artworks": [2631],
            "name": "Grand Theft Auto V",
            "summary": "Game summary"
        }]
        """;
        String artworkResponse = "[]";

        when(responseSpec.body(String.class))
                .thenReturn(gameResponse)
                .thenReturn(artworkResponse);

        IgdbGameAndArtwork result = igdbService.searchGameAndArtworkByName("GTA V");

        assertThat(result).isNull();
    }
}