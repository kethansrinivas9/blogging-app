package com.portfolio.blogging.controller;

import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.service.BlogService;
import com.portfolio.blogging.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    public Blog create(@RequestBody Blog blog) throws Exception {
        return blogService.create(blog);
    }

}
