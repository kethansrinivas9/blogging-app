package com.portfolio.blogging.dto;

import com.portfolio.blogging.entity.User;
import lombok.*;

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
