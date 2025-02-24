package com.portfolio.blogging.dto;

import com.portfolio.blogging.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    @Builder.Default
    private String password="";

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
