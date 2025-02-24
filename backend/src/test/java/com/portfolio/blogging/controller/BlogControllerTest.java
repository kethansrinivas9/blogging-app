package com.portfolio.blogging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.blogging.dto.BlogDTO;
import com.portfolio.blogging.entity.Blog;
import com.portfolio.blogging.service.BlogService;
import com.portfolio.blogging.service.CustomUserDetailsService;
import com.portfolio.blogging.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;


@WebMvcTest(BlogController.class)
@WithMockUser(username = "user")
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BlogService blogService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private Blog blog;

    private BlogDTO blogDTO;
    private String blogDTOJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        blog = Blog.builder().id(1L).title("Sample Title").content("Sample Content").build();
        blogDTO = BlogDTO.builder().title("Sample Title").content("Sample Content").id(1L).userName("user").userId(1L).build();

        // Use ObjectMapper to convert the BlogDTO object into JSON
        ObjectMapper objectMapper = new ObjectMapper();
        blogDTOJson = objectMapper.writeValueAsString(blogDTO);
    }

    @Test
    void testCreateBlogWithBlogDTOShouldReturnBlogCreated() throws Exception {
        Mockito.when(blogService.create(any(Blog.class))).thenReturn(blog);

        mockMvc.perform(post("/blog/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Title"))
                .andExpect(jsonPath("$.content").value("Sample Content"));
    }

    @Test
    void testGetAllBlogsShouldReturnAllBlogs() throws Exception {
        Mockito.when(blogService.getAllBlogs()).thenReturn(List.of(blogDTO));

        mockMvc.perform(get("/blog/all")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Sample Title"))
                .andExpect(jsonPath("$[0].content").value("Sample Content"))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].userName").value("user"));;

    }

}
