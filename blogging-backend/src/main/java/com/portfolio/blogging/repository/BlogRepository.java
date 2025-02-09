package com.portfolio.blogging.repository;

import com.portfolio.blogging.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId); // Query method to find blogs by user ID
}
