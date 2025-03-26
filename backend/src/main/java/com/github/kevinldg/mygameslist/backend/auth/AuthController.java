package com.github.kevinldg.mygameslist.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public Token register(@RequestBody AuthDTO authDTO) {
        String token = authService.register(authDTO.username(), authDTO.password());
        return new Token(token);
    }

    @PostMapping("/login")
    public Token login(@RequestBody AuthDTO authDTO) {
        String token = authService.login(authDTO.username(), authDTO.password());
        return new Token(token);
    }

    // Testing purposes
    @GetMapping("/me")
    public String me() {
        return "Authentication successful!";
    }
}
