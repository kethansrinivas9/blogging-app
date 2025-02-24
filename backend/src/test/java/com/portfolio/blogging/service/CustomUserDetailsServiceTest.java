package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("name").email("name@email.com").password("password").build();

        userDTO = new UserDTO(user);
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(user));

        User resultUser = customUserDetailsService.loadUserByUsername("name@email.com");

        assertThat(userDTO).isNotNull();
        assertThat(resultUser.getId()).isEqualTo(user.getId());
        assertThat(resultUser.getName()).isEqualTo(user.getName());
        assertThat(resultUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(resultUser.getPassword()).isEqualTo(user.getPassword());
    }
}