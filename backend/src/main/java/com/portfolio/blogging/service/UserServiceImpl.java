package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BlogService blogService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserDTO findById(Long userId) {
        UserDTO user = new UserDTO(userRepository.findById(userId).get());
        return user;
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).get();

        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
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
        if(findById(userId) != null)
            return blogService.getBlogsByUser(userId);

        throw new Exception("User not found with given id: " + userId);
    }
}
