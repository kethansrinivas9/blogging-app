package com.portfolio.blogging.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blogging.config.WebSecurityConfig;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.CustomUserDetailsService;
import com.portfolio.blogging.service.JWTService;
import com.portfolio.blogging.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="user")
@WebMvcTest(UserController.class)
@Import(WebSecurityConfig.class)
//This will help load the properties required to load the context that helps testing authentication logic
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    private UserDTO userDTO;

    private UserDTO updatedUser;
    private String userJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        user = User.builder().id(1L).name("user").password("password").email("user@email.com").build();

        userDTO = UserDTO.builder().id(1L).name("user").email("user@email.com").build();

        updatedUser = UserDTO.builder().id(1L).name("username").email("username@email.com").build();

        // Use ObjectMapper to convert the BlogDTO object into JSON
        ObjectMapper objectMapper = new ObjectMapper();
        userJson = objectMapper.writeValueAsString(updatedUser);
    }

    @Test
    void testGetUserByIDReturnsUser() throws Exception {
        Mockito.when(userService.findById(any(Long.class))).thenReturn(user);


        mockMvc.perform(get("/user/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.email").value("user@email.com"));

    }

    @Test
    void testGetUserByEmailReturnsUser() throws Exception {
        Mockito.when(userService.findByEmail(any(String.class))).thenReturn(userDTO);

        mockMvc.perform(get("/user/email/user@email.com")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.email").value("user@email.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testGetAllUsersReturnsAllUsers() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/all")
                        .with(csrf()))
                .andExpect(authenticated().withRoles("USER","ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("user"))
                .andExpect(jsonPath("$[0].email").value("user@email.com"));
    }

    @Test
    void testGetAllUsersReturnsForbiddenWhenAdminRoleIsNotPresent() throws Exception {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        Mockito.when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/all")
                        .with(csrf())
                )
                .andExpect(authenticated().withRoles("USER"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testEditUserReturnsUpdatedUser() throws Exception {
        Mockito.when(userService.editUserById(any(Long.class), any(UserDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/user/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.email").value("username@email.com"));
    }

}
