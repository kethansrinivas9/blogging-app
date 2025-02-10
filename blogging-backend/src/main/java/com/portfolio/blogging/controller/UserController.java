package com.portfolio.blogging.controller;


import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        LOGGER.info("===========user object {}", user.toString());
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping("/{id}")
    public User editUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        return userService.editUserById(id, user);
    }

    @GetMapping("/{userId}/blogs")
    public List<Blog> getBlogsByUser(@PathVariable Long userId) throws Exception {
        return userService.getBlogsByUser(userId);
    }

}
