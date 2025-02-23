package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("name").email("name@email.com").password("password").build();

        userDTO = new UserDTO(user);
    }

    @Test
    void register_ShouldSaveUserAndReturnUserDTO() {
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO registeredUser = authService.register(userDTO);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getName()).isEqualTo("name");
        assertThat(registeredUser.getEmail()).isEqualTo("name@email.com");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loginShouldAuthenticateUserAndReturnToken() {
        Authentication authentication = mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");

        String token = authService.login(user);

        assertThat(token).isEqualTo("mocked-jwt-token");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void loginShouldReturnFailureMessageWhenAuthenticationFails() {
        Authentication authentication = mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        String response = authService.login(user);

        assertThat(response).isEqualTo("Authentication Failure");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void logoutShouldOnlyClearSecurityContextWhenSessionIsNull() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Mockito.when(request.getSession(false)).thenReturn(null);

        authService.logout(request);

        assertThat(request.getSession(false)).isEqualTo(null);
        verify(request, times(2)).getSession(false);
    }
}