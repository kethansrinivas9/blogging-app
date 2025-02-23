package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.repository.BlogRepository;
import com.portfolio.blogging.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;

//@SpringBootTest(properties = "spring.profiles.active=test")
@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlogServiceImpl blogService;

    private Blog blog;
    private Blog blog2;
    private User user;
    private List<Blog> blogs;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("user").email("user@email.com").build();
        blog = Blog.builder().id(1L).title("title").content("content").user(user).build();
        blog2 = Blog.builder().id(2L).title("title2").content("content2").user(user).build();
        blogs = List.of(blog, blog2);
    }

    @Test
    void create() throws Exception {
        Mockito.when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        Blog createdBlog = blogService.create(blog);

        assertThat(createdBlog).isNotNull();
        assertThat(createdBlog.getId()).isEqualTo(1L);
        assertThat(createdBlog.getTitle()).isEqualTo("title");
        assertThat(createdBlog.getContent()).isEqualTo("content");
        assertThat(createdBlog.getUser().getId()).isEqualTo(1L);
    }

    @Test
    void getBlogsByUser() {
        Mockito.when(blogRepository.findByUserId(any(Long.class))).thenReturn(List.of(blog));

        List<BlogDTO> blogs = blogService.getBlogsByUser(1L);

        assertThat(blogs).hasSize(1);
        assertThat(blogs.get(0).getId()).isEqualTo(1L);
        assertThat(blogs.get(0).getTitle()).isEqualTo("title");
        assertThat(blogs.get(0).getContent()).isEqualTo("content");
        assertThat(blogs.get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    void getAllBlogs() {
        Mockito.when(blogRepository.findAll()).thenReturn(blogs);

        List<BlogDTO> returnedBlogs = blogService.getAllBlogs();

        assertThat(returnedBlogs).hasSize(2);

        assertThat(returnedBlogs.get(0).getId()).isEqualTo(1L);
        assertThat(returnedBlogs.get(0).getTitle()).isEqualTo("title");
        assertThat(returnedBlogs.get(0).getContent()).isEqualTo("content");
        assertThat(returnedBlogs.get(0).getUserId()).isEqualTo(1L);

        assertThat(returnedBlogs.get(1).getId()).isEqualTo(2L);
        assertThat(returnedBlogs.get(1).getTitle()).isEqualTo("title2");
        assertThat(returnedBlogs.get(1).getContent()).isEqualTo("content2");
        assertThat(returnedBlogs.get(1).getUserId()).isEqualTo(1L);

    }
}