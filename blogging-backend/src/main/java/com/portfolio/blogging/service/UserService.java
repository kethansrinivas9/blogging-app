package com.portfolio.blogging.service;

import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long userId);

    List<User> findAll();

    User editUserById(Long id, User user) throws Exception;

    List<Blog> getBlogsByUser(Long userId) throws Exception;

}
