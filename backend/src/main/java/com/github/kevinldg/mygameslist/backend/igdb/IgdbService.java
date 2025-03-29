package com.github.kevinldg.mygameslist.backend.igdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinldg.mygameslist.backend.exception.IgdbException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class IgdbService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public IgdbService(
            RestClient.Builder restClientBuilder,
            ObjectMapper objectMapper,
            @Value("${igdb.client.id}") String clientId,
            @Value("${igdb.bearer.token}") String bearerToken
    ) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.igdb.com/v4")
                .defaultHeader("Client-ID", clientId)
                .defaultHeader("Authorization", "Bearer " + bearerToken)
                .build();
        this.objectMapper = objectMapper;
    }

    public IgdbGame searchGameByName(String name) {
        String query = String.format("search \"%s\"; fields id, artworks, name, summary;", name);
        String response = restClient.post().uri("/games").body(query).retrieve().body(String.class);

        try {
            IgdbGame[] games = objectMapper.readValue(response, IgdbGame[].class);

            for (IgdbGame game : games) {
                if (game.artworks() != null && game.summary() != null) {
                    return new IgdbGame(
                            game.id(),
                            List.of(game.artworks().getFirst()),
                            game.name(),
                            game.summary()
                    );
                }
            }

            return null;
        } catch (Exception error) {
            throw new IgdbException("Error when processing the IGDB game: ", error);
        }
    }

    public IgdbArtwork searchArtworkById(String id) {
        String query = String.format("where game = %s; fields game, url; limit 1;", id);
        String response = restClient.post().uri("/artworks").body(query).retrieve().body(String.class);

        try {
            IgdbArtwork[] artworks = objectMapper.readValue(response, IgdbArtwork[].class);

            if (artworks != null && artworks.length > 0) {
                return artworks[0];
            }

            return null;
        } catch (Exception error) {
            throw new IgdbException("Error when processing the IGDB artwork: ", error);
        }
    }

    public IgdbGameAndArtwork searchGameAndArtworkByName(String name) {
        IgdbGame game = searchGameByName(name);

        if (game == null) {
            return null;
        }

        IgdbArtwork artwork = searchArtworkById(String.valueOf(game.id()));

        if (artwork == null) {
            return null;
        }


        return new IgdbGameAndArtwork(game.id(), game.name(), game.summary(), artwork.id(), artwork.url());
    }
}
