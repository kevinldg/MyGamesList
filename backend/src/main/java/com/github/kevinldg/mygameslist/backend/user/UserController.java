package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.Game;
import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public UserInfoDTO getUserByName(@PathVariable String username) {
        User user = userService.getUserByName(username);
        return new UserInfoDTO(user.id(), user.username(), user.createdAt(), user.games(), user.favoriteGame());
    }

    @GetMapping
    public List<UserInfoDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> new UserInfoDTO(
                        user.id(),
                        user.username(),
                        user.createdAt(),
                        user.games(),
                        user.favoriteGame()
                ))
                .toList();
    }

    @PostMapping("/games")
    public List<Game> addGameToUser(Authentication authentication, @RequestBody GameDTO gameDTO) { return userService.addGameToUser(authentication.getName(), gameDTO); }

    @DeleteMapping("/games")
    public List<Game> deleteGameFromUser(Authentication authentication, @RequestParam String gameName) { return userService.deleteGameFromUser(authentication.getName(), gameName); }

    @PutMapping("/games")
    public List<Game> updateGameFromUser(Authentication authentication, @RequestBody GameDTO gameDTO) { return userService.updateGameFromUser(authentication.getName(), gameDTO); }

    @PostMapping("/games/favorite")
    public Game setFavoriteGameFromUser(Authentication authentication, @RequestBody Game game) { return userService.setFavoriteGameFromUser(authentication.getName(), game); }
}
