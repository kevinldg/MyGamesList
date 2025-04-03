package com.github.kevinldg.mygameslist.backend.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    void registerShouldReturnToken() throws Exception {
        String requestBody = """
                {
                    "username": "testuser",
                    "password": "password123!",
                    "repeatPassword": "password123!"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DirtiesContext
    void loginShouldReturnToken() throws Exception {
        String registerRequestBody = """
                {
                    "username": "testuser",
                    "password": "password123!",
                    "repeatPassword": "password123!"
                }
                """;

        String loginRequestBody = """
                {
                    "username": "testuser",
                    "password": "password123!"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestBody));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DirtiesContext
    void getUserInfoShouldReturnUserInfo() throws Exception {
        String registerRequestBody = """
            {
                "username": "testuser",
                "password": "password123!",
                "repeatPassword": "password123!"
            }
            """;

        String responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerRequestBody))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String token = jsonNode.get("token").asText();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void getUserInfoShouldReturnUnauthorizedForInvalidToken() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkludmFsaWQgVG9rZW4iLCJpYXQiOjE1MTYyMzkwMjJ9.invalidSignature";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isForbidden());
    }
}