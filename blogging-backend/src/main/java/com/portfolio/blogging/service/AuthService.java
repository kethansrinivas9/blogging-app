package com.portfolio.blogging.service;

import com.portfolio.blogging.entity.User;

public interface AuthService {

    User register(User user);

    String login(User user);

}
