package com.portfolio.blogging.service;

import com.portfolio.blogging.controller.UserController;
import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BlogService blogService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).get();

        return new UserDTO(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO editUserById(Long id, UserDTO user) throws Exception {
        return userRepository.findById(id).map(
                existinguser -> {
                    existinguser.setEmail(user.getEmail());
                    existinguser.setName(user.getName());

                    User updatedUser = userRepository.save(existinguser);
                    return new UserDTO(updatedUser);
                }
        ).orElseThrow(() -> new Exception("User not found with given id"));
    }

    @Override
    public List<BlogDTO> getBlogsByUser(Long userId) throws Exception {
        User user = findById(userId);

        if(user!=null)
            return blogService.getBlogsByUser(userId);

        throw new Exception("User not found with given id: " + userId);
    }
}
