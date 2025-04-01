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

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    public User addGameToUser(String userId, GameDTO gameDTO) {
        User user = getUserById(userId);

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
        return userRepository.save(updatedUser);
    }

    public User deleteGameFromUser(String userId, String gameName) {
        User user = getUserById(userId);

        if (user.games().stream().noneMatch(game ->
                game.gameName().equals(gameName))) {
            throw new IllegalArgumentException("Game not included");
        }

        List<Game> updatedGames = user.games().stream()
                .filter(game -> !Objects.equals(game.gameName(), gameName))
                .collect(Collectors.toList());

        return userRepository.save(user.withGames(updatedGames));
    }

    public User updateGameFromUser(String userId, GameDTO gameDTO) {
        User user = getUserById(userId);

        if (user.games().stream().noneMatch(game ->
                game.gameName().equals(gameDTO.gameName()))) {
            throw new IllegalArgumentException("Game not included");
        }

        List<Game> updatedGames = user.games().stream()
                .map(gameFromUser -> Objects.equals(gameFromUser.gameName(), gameDTO.gameName())
                        ? gameFromUser.withGameState(gameDTO.gameState())
                        : gameFromUser)
                .collect(Collectors.toList());

        return userRepository.save(user.withGames(updatedGames));
    }
}
