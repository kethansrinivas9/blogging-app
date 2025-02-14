package com.portfolio.blogging.dto;

import com.portfolio.blogging.entity.Blog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class BlogDTO {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;


    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.userId = blog.getUser().getId();
        this.userName = blog.getUser().getName();
    }
}
