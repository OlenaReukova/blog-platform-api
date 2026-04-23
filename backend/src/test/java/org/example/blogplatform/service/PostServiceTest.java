package org.example.blogplatform.service;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    // creates a FAKE postRepository
    @Mock
    private PostRepository postRepository;


    // creates a REAL PostService but injects the fake repository into it
    @InjectMocks
    private PostService postService;

    @Test
    void shouldReturnAllPosts() {
        //Given
        Post post1 = new Post();
        post1.setTitle("First Post");

        Post post2 = new Post();
        post2.setTitle("Second Post");

        //when findAll() is called, return these 2 posts
        when(postRepository.findAll()).thenReturn(List.of(post1, post2));

        // WHEN — call the actual method you are testing
        List<Post> result = postService.getAllPosts();

        // THEN — check the result is what you expect
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("First Post");
        verify(postRepository, times(1)).findAll();
        // verify() checks that findAll() was called exactly once
        // confirms your service actually used the repository

    }
    @Test
    void shouldReturnEmptyListWhenNoPostsExist() {
        // given
        when(postRepository.findAll()).thenReturn(List.of());

        // when
        List<Post> result = postService.getAllPosts();

        // then
        assertThat(result).isEmpty();
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void shouldCreatePost(){
        //given
        Post post = new Post();
        post.setTitle("New Post");
        post.setAuthor("Alan Parker");
        post.setContent("Content");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        //when
        Post result = postService.createPost(post);

        //then
        assertThat(result.getTitle()).isEqualTo("New Post");
        verify(postRepository, times(1)).save(post);
    }



}
