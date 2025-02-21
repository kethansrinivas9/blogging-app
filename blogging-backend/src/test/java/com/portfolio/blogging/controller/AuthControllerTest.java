package com.portfolio.blogging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.AuthService;
import com.portfolio.blogging.service.CustomUserDetailsService;
import com.portfolio.blogging.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private User user;
    private String userJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        user = User.builder().name("name").email("name@email.com").password("password").build();

        // Use ObjectMapper to convert the BlogDTO object into JSON
        ObjectMapper objectMapper = new ObjectMapper();
        userJson = objectMapper.writeValueAsString(user);
    }

    @Test
    void register() throws Exception {
        Mockito.when(authService.login(any(User.class))).thenReturn("User registered successfully!");

        mockMvc.perform(post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isCreated());
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }
}