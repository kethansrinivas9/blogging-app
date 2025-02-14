package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.entity.Blog;

import java.util.List;

public interface BlogService {
    Blog create(Blog blog) throws Exception;

    List<Blog> getBlogsByUser(Long userId);

    List<BlogDTO> getAllBlogs();
}
