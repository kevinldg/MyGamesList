package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Builder
@Document(collection = "users")
public record User(
        @Id String id,
        String username,
        String password,
        Instant createdAt,
        List<Game> games,
        Game favoriteGame
) {
    public User withGames(List<Game> games) {
        return new User(this.id(), this.username(), this.password(), this.createdAt(), games, this.favoriteGame);
    }

    public User withFavoriteGame(Game game) {
        return new User(this.id(), this.username(), this.password(), this.createdAt(), this.games(), game);
    }
}
