package com.portfolio.blogging.service;

import com.portfolio.blogging.controller.UserController;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final BlogService blogService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        if(authentication.isAuthenticated())
            return "Authentication Success!";

        return "Authentication Failure";
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User editUserById(Long id, User user) throws Exception {
        return userRepository.findById(id).map(
                existinguser -> {
                    existinguser.setEmail(user.getEmail());
                    existinguser.setName(user.getName());
                    existinguser.setPassword(user.getPassword());

                    return userRepository.save(existinguser);
                }
        ).orElseThrow(() -> new Exception("User not found with given id"));
    }

    @Override
    public List<Blog> getBlogsByUser(Long userId) throws Exception {
        User user = findById(userId);

        if(user!=null)
            return blogService.getBlogsByUser(userId);

        throw new Exception("User not found with given id: " + userId);
    }
}
