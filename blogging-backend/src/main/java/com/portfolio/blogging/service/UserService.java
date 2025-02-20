package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long userId);

    UserDTO findByEmail(String email);

    List<User> findAll();

    UserDTO editUserById(Long id, UserDTO user) throws Exception;

    List<BlogDTO> getBlogsByUser(Long userId) throws Exception;

}
