package com.portfolio.blogging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blogging.config.JWTAuthenticationFilter;
import com.portfolio.blogging.config.WebSecurityConfig;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.AuthService;
import com.portfolio.blogging.service.CustomUserDetailsService;
import com.portfolio.blogging.service.JWTService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(WebSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private UserDTO userDTO;
    private String userJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        userDTO = UserDTO.builder().name("name").email("name@email.com").password("password").build();

        // Use ObjectMapper to convert the BlogDTO object into JSON
        ObjectMapper objectMapper = new ObjectMapper();
        userJson = objectMapper.writeValueAsString(userDTO);
    }

    @Test
    void register() throws Exception {
        Mockito.when(authService.register(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
        .andDo(print())
        .andExpect(status().isCreated());
    }

    @Test
    void login() throws Exception {
        Mockito.when(authService.login(any(User.class))).thenReturn("dfasdfasdfjasdf;alskd");

        mockMvc.perform(post("/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isOk());
    }

    @Test
    void logout() throws Exception {

        mockMvc.perform(post("/auth/logout")
                .with(csrf())
        ).andExpect(status().isOk());
    }
}