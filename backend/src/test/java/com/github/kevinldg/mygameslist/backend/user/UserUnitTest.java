package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import com.github.kevinldg.mygameslist.backend.game.GameState;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbGameAndArtwork;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

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
        Game favoriteGame = new Game(1, "1", "1", 1, "1", GameState.COMPLETED);
        testUser = new User("uniqueId", "testUser", "passwordHash", Instant.now(), games, favoriteGame);
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
    void testGetAllUsers() {
        List<User> users = List.of(
                new User("id1", "user1", "passwordHash1", Instant.now(), new ArrayList<>(), null),
                new User("id2", "user2", "passwordHash2", Instant.now(), new ArrayList<>(), null)
        );

        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).username());
        assertEquals("user2", result.get(1).username());

        verify(userRepository, times(1)).findAll();
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

    @Test
    void testDeleteGameFromUser_Success() {
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        testUser = new User("uniqueId", "testUser", "passwordHash", Instant.now(), new ArrayList<>(Collections.singletonList(game)), game);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(i -> i.getArgument(0));

        List<Game> updatedGames = userService.deleteGameFromUser("testUser", "Halo");
        assertTrue(updatedGames.isEmpty());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testUpdateGameFromUser_Success() {
        Game game = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        testUser = new User("uniqueId", "testUser", "passwordHash", Instant.now(), new ArrayList<>(Collections.singletonList(game)), game);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenAnswer(i -> i.getArgument(0));

        GameDTO gameDTO = new GameDTO("Halo", GameState.COMPLETED);
        List<Game> updatedGames = userService.updateGameFromUser("testUser", gameDTO);

        assertEquals(1, updatedGames.size());
        assertEquals(GameState.COMPLETED, updatedGames.getFirst().gameState());
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void testSetFavoriteGameFromUser() {
        Game favoriteGame = new Game(123, "Halo", "Shooter Game", 123, "http://url", GameState.PLAYING);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        Game result = userService.setFavoriteGameFromUser("testUser", favoriteGame);
        assertEquals("Halo", result.gameName());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("Halo", savedUser.favoriteGame().gameName());
    }

    @Test
    void testWithFavoriteGame() {
        Game newFavorite = new Game(2, "Halo 2", "Shooter Game", 456, "http://newurl", GameState.PLAYING);
        User updatedUser = testUser.withFavoriteGame(newFavorite);
        assertEquals("Halo 2", updatedUser.favoriteGame().gameName());
        assertEquals(testUser.id(), updatedUser.id());
        assertEquals(testUser.username(), updatedUser.username());
    }
}