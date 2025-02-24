package com.portfolio.blogging.controller;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<Blog> create(@RequestBody BlogDTO blogDTO) throws Exception {

        try {
            Blog blog = new Blog();
            blog.setTitle(blogDTO.getTitle());
            blog.setContent(blogDTO.getContent());

            //Creating the user with just id;
            User user = new User();
            user.setId(blogDTO.getUserId());
            blog.setUser(user);

            Blog createdBlog = blogService.create(blog);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdBlog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        try {
            List<BlogDTO> blogs = blogService.getAllBlogs();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(blogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
}
