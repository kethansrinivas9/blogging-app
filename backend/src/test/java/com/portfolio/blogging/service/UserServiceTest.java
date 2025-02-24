package com.portfolio.blogging.service;

import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.dto.UserDTO;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private BlogServiceImpl blogService;

    private UserDTO userDTO;
    private List<Blog> blogs;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("name").email("name@email.com").password("password").build();
        userDTO = new UserDTO(user);

        blogs = List.of(Blog.builder().id(1L).title("title").content("content").user(user).build());
    }

    @Test
    void findById() {
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDTO returnedUser = userService.findById(1L);

        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getId()).isEqualTo(1L);
        assertThat(returnedUser.getName()).isEqualTo("name");
        assertThat(returnedUser.getEmail()).isEqualTo("name@email.com");
    }

    @Test
    void findByEmail() {
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        UserDTO returnedUser = userService.findByEmail("name@email.com");

        assertThat(returnedUser).isNotNull();
        assertThat(returnedUser.getId()).isEqualTo(1L);
        assertThat(returnedUser.getName()).isEqualTo("name");
        assertThat(returnedUser.getEmail()).isEqualTo("name@email.com");
    }

    @Test
    void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> returnedUsers = userService.findAll();

        assertThat(returnedUsers).isNotNull();
        assertThat(returnedUsers).hasSize(1);
        assertThat(returnedUsers.get(0).getId()).isEqualTo(1L);
        assertThat(returnedUsers.get(0).getName()).isEqualTo("name");
        assertThat(returnedUsers.get(0).getEmail()).isEqualTo("name@email.com");
    }

    @Test
    void editUserById() throws Exception {
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDTO editedUserDetails = UserDTO.builder().id(1L).name("newname").email("newname@email.com").build();
        UserDTO returnedUserDetails = userService.editUserById(1L, editedUserDetails);

        assertThat(returnedUserDetails).isNotNull();
        assertThat(returnedUserDetails.getId()).isEqualTo(editedUserDetails.getId());
        assertThat(returnedUserDetails.getEmail()).isEqualTo(editedUserDetails.getEmail());
        assertThat(returnedUserDetails.getName()).isEqualTo(editedUserDetails.getName());
    }

    @Test
    void getBlogsByUser() {
        Mockito.when(blogRepository.findByUserId(any(Long.class))).thenReturn(blogs);

        List<BlogDTO> returnedBlogs = blogService.getBlogsByUser(1L);
        assertThat(returnedBlogs).isNotNull();
        assertThat(returnedBlogs).hasSize(blogs.size());
        assertThat(returnedBlogs.get(0).getUserId()).isEqualTo(1L);
        assertThat(returnedBlogs.get(0).getTitle()).isEqualTo("title");
        assertThat(returnedBlogs.get(0).getContent()).isEqualTo("content");
        assertThat(returnedBlogs.get(0).getId()).isEqualTo(1L);
    }
}