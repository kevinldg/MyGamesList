package com.github.kevinldg.mygameslist.backend.auth;

import com.github.kevinldg.mygameslist.backend.exception.UserAlreadyExistsException;
import com.github.kevinldg.mygameslist.backend.user.User;
import com.github.kevinldg.mygameslist.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);

        return jwtService.generateToken(username);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("No user found"));

        if (!passwordEncoder.matches(password, user.password())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return jwtService.generateToken(username);
    }
}
