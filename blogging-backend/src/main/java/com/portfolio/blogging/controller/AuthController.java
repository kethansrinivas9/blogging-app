package com.portfolio.blogging.controller;

import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        logger.info("===========user object {}", user.toString());

        authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        logger.info("==========user object {}", user.toString());



        try {
            String jwt = authService.login(user);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login unsuccessful due to: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }

        // Remove JSESSIONID cookie
        //response.setHeader("Set-Cookie", "JSESSIONID=; Path=/; HttpOnly; Secure; Max-Age=0");
    }
}
