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
    void testGetUserByName_Found() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        User user = userService.getUserByName("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.username());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testGetUserByName_NotFound() {
        when(userRepository.findByUsername("wrongUser")).thenReturn(Optional.empty());
        Exception ex = assertThrows(NoSuchElementException.class, () -> userService.getUserByName("wrongUser"));
        assertTrue(ex.getMessage().contains("User not found with name: wrongUser"));
    }

    @Test
    void testAddGameToUser_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        IgdbGameAndArtwork igdbResult = new IgdbGameAndArtwork(123, "Halo", "Shooter Game", 123, "http://url");
        when(igdbService.searchGameAndArtworkByName("Halo")).thenReturn(igdbResult);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(i -> i.getArgument(0));

        GameDTO gameDTO = new GameDTO("Halo", GameState.PLAYING);
        List<Game> updatedUser = userService.addGameToUser("testUser", gameDTO);

        assertEquals(1, updatedUser.size());
        Game addedGame = updatedUser.getFirst();
        assertEquals("Halo", addedGame.gameName());
        assertEquals(GameState.PLAYING, addedGame.gameState());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }
}