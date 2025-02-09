package com.portfolio.blogging.service;

import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.BlogRepository;
import com.portfolio.blogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;


    public Blog create(Blog blog) throws Exception {
        Long userId = blog.getUser().getId();
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()) {
            return blogRepository.save(blog);
        }

        throw  new Exception("User not found with the userID " + userId);
    }

    @Override
    public List<Blog> getBlogsByUser(Long userId) {
        return blogRepository.findByUserId(userId);
    }
}
