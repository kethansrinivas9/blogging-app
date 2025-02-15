package com.portfolio.blogging.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDTO {
    private final Long id;
    private final String name;
    private final String email;
}
