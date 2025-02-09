package com.portfolio.blogging.service;

import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    private BlogService blogService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User register(User user) {

        return userRepository.save(user);
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
