package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import com.github.kevinldg.mygameslist.backend.game.GameState;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbGameAndArtwork;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IgdbService igdbService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setup() {
        List<Game> games = new ArrayList<>();
        testUser = new User("uniqueId", "testUser", "passwordHash", Instant.now(), games);
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));

        User user = userService.getUserById("testId");
        assertNotNull(user);
        assertEquals("testUser", user.username());
        verify(userRepository, times(1)).findById("testId");
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById("wrongId")).thenReturn(Optional.empty());
        Exception ex = assertThrows(NoSuchElementException.class, () -> userService.getUserById("wrongId"));
        assertTrue(ex.getMessage().contains("User not found with id: wrongId"));
    }

    @Test
    void testAddGameToUser_Success() {
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        IgdbGameAndArtwork igdbResult = new IgdbGameAndArtwork(123, "Halo", "Shooter Game", 123, "http://url");
        when(igdbService.searchGameAndArtworkByName("Halo")).thenReturn(igdbResult);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(i -> i.getArgument(0));

        GameDTO gameDTO = new GameDTO("Halo", GameState.PLAYING);

        User updatedUser = userService.addGameToUser("testId", gameDTO);

        assertEquals(1, updatedUser.games().size());
        Game addedGame = updatedUser.games().getFirst();
        assertEquals("Halo", addedGame.gameName());
        assertEquals(GameState.PLAYING, addedGame.gameState());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testAddGameToUser_AlreadyExists() {
        Game existingGame = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        testUser.games().add(existingGame);
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));

        GameDTO gameDTO = new GameDTO("Halo", GameState.PLAYING);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.addGameToUser("testId", gameDTO));
        assertTrue(ex.getMessage().contains("Game already added"));
    }

    @Test
    void testDeleteGameFromUser_Success() {
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        testUser.games().add(game);
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User updatedUser = userService.deleteGameFromUser("testId", "Halo");
        assertTrue(updatedUser.games().isEmpty());
    }

    @Test
    void testDeleteGameFromUser_GameNotIncluded() {
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.deleteGameFromUser("testId", "NonExistingGame"));
        assertTrue(ex.getMessage().contains("Game not included"));
    }

    @Test
    void testUpdateGameFromUser_Success() {
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        testUser.games().add(game);
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        GameDTO gameDTO = new GameDTO("Halo", GameState.COMPLETED);

        User updatedUser = userService.updateGameFromUser("testId", gameDTO);
        assertEquals(1, updatedUser.games().size());
        Game updatedGame = updatedUser.games().getFirst();
        assertEquals(GameState.COMPLETED, updatedGame.gameState());
    }

    @Test
    void testUpdateGameFromUser_GameNotIncluded() {
        when(userRepository.findById("testId")).thenReturn(Optional.of(testUser));
        GameDTO gameDTO = new GameDTO("NonExistingGame", GameState.COMPLETED);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.updateGameFromUser("testId", gameDTO));
        assertTrue(ex.getMessage().contains("Game not included"));
    }
}