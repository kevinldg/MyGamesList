package com.github.kevinldg.mygameslist.backend.user;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "users")
public record User(
        @Id String id,
        String username,
        String password
) {}
