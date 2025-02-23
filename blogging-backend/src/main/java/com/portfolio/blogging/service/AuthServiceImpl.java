package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDTO register(UserDTO userDTO) {
        User user = User.builder().name(userDTO.getName()).email(userDTO.getEmail()).password(bCryptPasswordEncoder.encode(userDTO.getPassword())).build();

        return new UserDTO(userRepository.save(user));
    }


    @Override
    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user);

        return "Authentication Failure";
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            SecurityContextHolder.clearContext();
            session.invalidate(); // Destroy session
        }
    }
}
