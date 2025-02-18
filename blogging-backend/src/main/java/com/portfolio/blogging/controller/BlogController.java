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
import java.util.Map;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestBody BlogDTO blogDTO) throws Exception {


        try {
            Blog blog = new Blog();
            blog.setTitle(blogDTO.getTitle());
            blog.setContent(blogDTO.getContent());

            //Creating the user with just id;
            User user = new User();
            user.setId(blogDTO.getUserId());
            blog.setUser(user);
            blogService.create(blog);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Blog created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong", "message", e.getMessage()));
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
