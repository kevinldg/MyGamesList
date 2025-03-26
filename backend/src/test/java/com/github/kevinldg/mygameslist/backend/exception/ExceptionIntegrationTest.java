package com.github.kevinldg.mygameslist.backend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DirtiesContext
    void globalExceptionHandling_shouldReturnErrorMessage() throws Exception {
        String requestBody = """
                {
                    "username": "testuser",
                    "password": "password123"
                }
                """;

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "message": "No user found"
                                }
                                """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());

    }
}
