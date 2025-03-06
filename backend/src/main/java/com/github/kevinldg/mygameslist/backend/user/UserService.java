package com.github.kevinldg.mygameslist.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    public User createUser(UserCreateDTO userCreateDTO) {
        User newUser = new User(UUID.randomUUID().toString(), userCreateDTO.username(), userCreateDTO.password(), new ArrayList<>());
        return userRepository.save(newUser);

    }

    public User updateGamesFromUserById(UserGamesDTO userGamesDTO, String id) {
        User user = getUserById(id);
        User updatedUser = new User(user.id(), user.username(), user.password(), userGamesDTO.games());

        return userRepository.save(updatedUser);
    }
}
