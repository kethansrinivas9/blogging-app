package com.portfolio.blogging.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class UserDTO {
    private final Long id;
    private final String name;
    private final String email;
}
