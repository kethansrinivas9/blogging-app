package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.UserDTO;
import com.portfolio.blogging.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    UserDTO register(UserDTO user);

    String login(User user);

    void logout(HttpServletRequest request);

}
