package com.github.kevinldg.mygameslist.backend.igdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinldg.mygameslist.backend.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IgdbIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IgdbService igdbService;

    @Autowired
    private JwtService jwtService;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        jwtToken = jwtService.generateToken("testuser");
    }

    @Test
    @WithMockUser
    void searchGame_shouldReturnGameInformation() throws Exception {
        String gameName = "GTA V";
        String gameSummary = "Grand Theft Auto V is a vast open world game set in Los Santos, a sprawling sun-soaked " +
                "metropolis struggling to stay afloat in an era of economic uncertainty and cheap reality TV. " +
                "The game blends storytelling and gameplay in new ways as players repeatedly jump in and out of the " +
                "lives of the game's three lead characters, playing all sides of the game's interwoven story.";

        IgdbGame game = new IgdbGame(1020, List.of(2631), "Grand Theft Auto V", gameSummary);
        when(igdbService.searchGameByName(gameName)).thenReturn(game);

        mockMvc.perform(get("/api/igdb/games")
                        .param("name", gameName)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(game)));
    }

    @Test
    @WithMockUser
    void searchArtwork_shouldReturnArtworkInformation() throws Exception {
        String gameId = "1020";
        IgdbArtwork artwork = new IgdbArtwork(2636, 1020,
                "//images.igdb.com/igdb/image/upload/t_thumb/uzc3v8bb2gow8feq3eji.jpg");
        when(igdbService.searchArtworkById(gameId)).thenReturn(artwork);

        mockMvc.perform(get("/api/igdb/artworks")
                        .param("id", gameId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(artwork)));
    }

    @Test
    @WithMockUser
    void searchGameAndArtwork_shouldReturnCombinedInformation() throws Exception {
        String gameName = "GTA V";
        String gameSummary = "Grand Theft Auto V is a vast open world game set in Los Santos, a sprawling sun-soaked " +
                "metropolis struggling to stay afloat in an era of economic uncertainty and cheap reality TV. " +
                "The game blends storytelling and gameplay in new ways as players repeatedly jump in and out of the " +
                "lives of the game's three lead characters, playing all sides of the game's interwoven story.";

        IgdbGameAndArtwork gameAndArtwork = new IgdbGameAndArtwork(
                1020,
                "Grand Theft Auto V",
                gameSummary,
                2636,
                "//images.igdb.com/igdb/image/upload/t_thumb/uzc3v8bb2gow8feq3eji.jpg"
        );
        when(igdbService.searchGameAndArtworkByName(gameName)).thenReturn(gameAndArtwork);

        mockMvc.perform(get("/api/igdb/game-and-artwork")
                        .param("name", gameName)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(gameAndArtwork)));
    }

    @Test
    @WithMockUser
    void searchGame_whenGameNotFound_shouldReturnNotFound() throws Exception {
        String gameName = "NonExistentGame";
        when(igdbService.searchGameByName(gameName)).thenReturn(null);

        mockMvc.perform(get("/api/igdb/games")
                        .param("name", gameName)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser
    void searchGameAndArtwork_whenNotFound_shouldReturnNotFound() throws Exception {
        String gameName = "NonExistentGame";
        when(igdbService.searchGameAndArtworkByName(gameName)).thenReturn(null);

        mockMvc.perform(get("/api/igdb/game-and-artwork")
                        .param("name", gameName)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void unauthorized_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/igdb/games")
                        .param("name", "GTA V")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}