package com.github.kevinldg.mygameslist.backend.exception;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@AutoConfigureMockMvc
class InvalidGameStateExceptionIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new DummyController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @WithMockUser
    void invalidGameStateException_shouldReturnErrorResponse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/dummy/invalid-game-state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(Matchers.containsString("Invalid game state")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Configuration
    static class DummyControllerConfig {
        @Bean
        public DummyController dummyController() {
            return new DummyController();
        }
    }

    @RestController
    static class DummyController {
        @GetMapping("/dummy/invalid-game-state")
        public void triggerInvalidGameStateException() {
            throw new InvalidGameStateException("invalid_state");
        }
    }
}