package com.github.kevinldg.mygameslist.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
