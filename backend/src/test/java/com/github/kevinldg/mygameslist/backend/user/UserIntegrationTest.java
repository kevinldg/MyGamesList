package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import com.github.kevinldg.mygameslist.backend.game.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testId")
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String userId = "testId";

    @Test
    void testAddGameToUser() throws Exception {
        GameDTO gameDTO = new GameDTO("Halo", GameState.PLAYING);
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.singletonList(game), game);

        when(userService.addGameToUser(eq(userId), any(GameDTO.class))).thenReturn(user.games());

        mockMvc.perform(post("/api/user/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameName").value("Halo"));
    }

    @Test
    void testDeleteGameFromUser() throws Exception {
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.emptyList(), game);

        when(userService.deleteGameFromUser(eq(userId), eq("Halo"))).thenReturn(user.games());

        mockMvc.perform(delete("/api/user/games")
                        .param("gameName", "Halo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testUpdateGameFromUser() throws Exception {
        GameDTO gameDTO = new GameDTO("Halo", GameState.COMPLETED);
        Game updatedGame = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.COMPLETED);
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.singletonList(updatedGame), updatedGame);

        when(userService.updateGameFromUser(eq(userId), any(GameDTO.class))).thenReturn(user.games());

        mockMvc.perform(put("/api/user/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameState").value("COMPLETED"));
    }
}