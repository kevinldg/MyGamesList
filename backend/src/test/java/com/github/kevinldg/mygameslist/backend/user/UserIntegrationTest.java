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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
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
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.singletonList(game));

        when(userService.addGameToUser(eq(userId), any(GameDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/user/{userId}/games", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.games[0].gameName").value("Halo"));
    }

    @Test
    void testDeleteGameFromUser() throws Exception {
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.emptyList());

        when(userService.deleteGameFromUser(eq(userId), eq("Halo"))).thenReturn(user);

        mockMvc.perform(delete("/api/user/{userId}/games", userId)
                        .param("gameName", "Halo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games").isEmpty());
    }

    @Test
    void testUpdateGameFromUser() throws Exception {
        GameDTO gameDTO = new GameDTO("Halo", GameState.COMPLETED);
        Game updatedGame = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.COMPLETED);
        User user = new User(userId, "testUser", "hash", Instant.now(), Collections.singletonList(updatedGame));

        when(userService.updateGameFromUser(eq(userId), any(GameDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/user/{userId}/games", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games[0].gameState").value("COMPLETED"));
    }
}