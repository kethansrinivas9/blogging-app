package com.portfolio.blogging.repository;

import com.portfolio.blogging.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId); // Query method to find blogs by user ID

    @Query("SELECT b FROM Blog b JOIN FETCH b.user")
    List<Blog> findAllBlogs();
}
