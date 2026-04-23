package org.example.blogplatform.controller;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void shouldReturn200WithListOfPosts() throws Exception {
        //given
        Post post = new Post();
        post.setTitle("First Post");

        when(postService.getAllPosts()).thenReturn(List.of(post));

        //when + then
        mockMvc.perform(get("/api/posts")).andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value("First Post"));
    }

    @Test
    void shouldReturn200WithEmptyList() throws Exception {
        // given
        when(postService.getAllPosts()).thenReturn(List.of());

        // when + then
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
