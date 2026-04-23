package org.example.blogplatform.controller;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.service.PostService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


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

    @Test
    void shouldCreatePostAndReturn201() throws Exception {
        //given
        Post post = new Post();
        post.setId("123456");
        post.setTitle("New Post");
        post.setAuthor("Alan Parker");
        post.setContent("Content");

        when(postService.createPost(any(Post.class))).thenReturn(post);

        //when
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "New Post",
                        "author": "Alan Parker",
                        "content": "Content",
                        "tags": ["Design"]
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123456"))
                .andExpect(jsonPath("$.title").value("New Post"));
    }

    @Test
    void shouldDeletePostAndReturn204() throws Exception {
        //given
        String id = "123456";

        //when + then
        mockMvc.perform(delete("/api/posts/" + id))
                .andExpect(status().isNoContent());

    }
}
