package com.portfolio.blogging.controller;


import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    public User editUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        return userService.editUserById(id, user);
    }

    /*
     * This is the method to fetch all the blogs of current logged-in user
     * */
    @GetMapping("/{userId}/blog/all")
    public List<BlogDTO> getBlogsByUser(@PathVariable Long userId) throws Exception {
        return userService.getBlogsByUser(userId);
    }

}
