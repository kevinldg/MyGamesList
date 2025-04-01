package com.github.kevinldg.mygameslist.backend.user;

import com.github.kevinldg.mygameslist.backend.game.GameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("{userId}/games")
    public User addGameToUser(@PathVariable String userId, @RequestBody GameDTO gameDTO) { return userService.addGameToUser(userId, gameDTO); }

    @DeleteMapping("{userId}/games")
    public User deleteGameFromUser(@PathVariable String userId, @RequestParam String gameName) { return userService.deleteGameFromUser(userId, gameName); }

    @PutMapping("{userId}/games")
    public User updateGameFromUser(@PathVariable String userId, @RequestBody GameDTO gameDTO) { return userService.updateGameFromUser(userId, gameDTO); }
}
