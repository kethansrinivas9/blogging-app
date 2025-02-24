package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long userId);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    UserDTO editUserById(Long id, UserDTO user) throws Exception;

    List<BlogDTO> getBlogsByUser(Long userId) throws Exception;

}
