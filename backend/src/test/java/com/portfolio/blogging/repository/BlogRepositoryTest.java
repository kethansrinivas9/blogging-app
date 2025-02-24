package com.portfolio.blogging.repository;

import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    private Blog blog;
    private Blog blog2;
    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        user = User.builder().name("name").email("name@email.com").password("password").build();
        user2 = User.builder().name("name2").email("name2@email.com").password("password2").build();

        blog = Blog.builder().title("title").content("content").user(user).build();
        blog2 = Blog.builder().title("title2").content("content2").user(user).build();
    }

    @Test
    void testBlogCreation() {
        //Create User first to add blogs to it
        User updatedUser = userRepository.save(user);
        Blog savedBlog = blogRepository.save(blog);

        assertThat(savedBlog).isNotNull();
        assertThat(savedBlog.getId()).isGreaterThan(0);
        assertEquals(savedBlog.getTitle(),  blog.getTitle());
        assertEquals(savedBlog.getContent(), blog.getContent());
        assertEquals(savedBlog.getId(), blog.getId());
        assertEquals(savedBlog.getUser().getId(), blog.getUser().getId());
    }

    @Test
    void findByUserId() {
        User updatedUser = userRepository.save(user);
        Blog savedBlog = blogRepository.save(blog);

        List<Blog> blogs = blogRepository.findByUserId(updatedUser.getId());

        assertFalse(blogs.isEmpty());
        assertEquals(1, blogs.size());
        assertEquals(savedBlog.getTitle(), blogs.get(0).getTitle());
        assertEquals(savedBlog.getContent(), blogs.get(0).getContent());
        assertEquals(savedBlog.getId(), blogs.get(0).getId());
        assertEquals(savedBlog.getUser().getId(), blogs.get(0).getUser().getId());
    }

    @Test
    void findAllBlogs() {
        //Create user and blog
        userRepository.save(user);
        blogRepository.save(blog);

        //Create new user and new blog
        userRepository.save(user2);
        blogRepository.save(blog2);

        List<Blog> blogs = blogRepository.findAll();

        assertFalse(blogs.isEmpty());
        assertEquals(2, blogs.size());
        assertEquals(blog.getTitle(), blogs.get(0).getTitle());
        assertEquals(blog.getContent(), blogs.get(0).getContent());
        assertEquals(blog.getId(), blogs.get(0).getId());
        assertEquals(blog.getUser().getId(), blogs.get(0).getUser().getId());

        assertEquals(blog2.getTitle(), blogs.get(1).getTitle());
        assertEquals(blog2.getContent(), blogs.get(1).getContent());
        assertEquals(blog2.getId(), blogs.get(1).getId());
        assertEquals(blog2.getUser().getId(), blogs.get(1).getUser().getId());
    }

    @AfterEach
    void deleteData() {
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}