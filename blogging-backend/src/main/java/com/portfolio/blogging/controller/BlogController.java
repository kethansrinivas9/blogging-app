package com.portfolio.blogging.controller;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public Blog create(@RequestBody BlogDTO blogDTO) throws Exception {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());

        //Creating the user with just id;
        User user = new User();
        user.setId(blogDTO.getUserId());
        blog.setUser(user);

        return blogService.create(blog);
    }

    @GetMapping("/all")
    public List<BlogDTO> getAllBlogs() {
        return blogService.getAllBlogs();
    }
}
