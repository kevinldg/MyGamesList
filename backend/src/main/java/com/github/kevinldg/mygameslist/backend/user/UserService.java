package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbGameAndArtwork;
import com.github.kevinldg.mygameslist.backend.igdb.IgdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final IgdbService igdbService;

    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found with name: " + username));
    }

    public List<Game> addGameToUser(String username, GameDTO gameDTO) {
        User user = getUserByName(username);

        if (user.games().stream().anyMatch(game ->
                game.gameName().equals(gameDTO.gameName()))) {
            throw new IllegalArgumentException("Game already added");
        }

        IgdbGameAndArtwork igdbGameAndArtwork = igdbService.searchGameAndArtworkByName(gameDTO.gameName());
        Game gameToAdd = new Game(
                igdbGameAndArtwork.gameId(),
                igdbGameAndArtwork.gameName(),
                igdbGameAndArtwork.gameSummary(),
                igdbGameAndArtwork.artworkId(),
                igdbGameAndArtwork.artworkUrl(),
                gameDTO.gameState()
        );
        user.games().add(gameToAdd);

        User updatedUser = user.withGames(user.games());
        userRepository.save(updatedUser);

        return user.games();
    }

    public List<Game> deleteGameFromUser(String username, String gameName) {
        User user = getUserByName(username);

        if (user.games().stream().noneMatch(game ->
                game.gameName().equals(gameName))) {
            throw new IllegalArgumentException("Game not included");
        }

        List<Game> updatedGames = user.games().stream()
                .filter(game -> !Objects.equals(game.gameName(), gameName))
                .collect(Collectors.toList());
        userRepository.save(user.withGames(updatedGames));

        return updatedGames;
    }

    public List<Game> updateGameFromUser(String username, GameDTO gameDTO) {
        User user = getUserByName(username);

        if (user.games().stream().noneMatch(game ->
                game.gameName().equals(gameDTO.gameName()))) {
            throw new IllegalArgumentException("Game not included");
        }

        List<Game> updatedGames = user.games().stream()
                .map(gameFromUser -> Objects.equals(gameFromUser.gameName(), gameDTO.gameName())
                        ? gameFromUser.withGameState(gameDTO.gameState())
                        : gameFromUser)
                .collect(Collectors.toList());
        userRepository.save(user.withGames(updatedGames));

        return updatedGames;
    }
}
