package org.example.blogplatform.service;

import org.example.blogplatform.model.Comment;
import org.example.blogplatform.model.Post;
import org.example.blogplatform.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
    void shouldReturnPostById() {
        //given
        Post post = new Post();
        post.setId("123");
        when(postRepository.findById("123")).thenReturn(Optional.of(post));

        //when
        Post result = postService.getPostById("123");

        //then
        assertThat(result.getId()).isEqualTo("123");
    }

    @Test
    void shouldTrowExceptionWhenPostNotFound() {
        //given
        when(postRepository.findById("999")).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> postService.getPostById("999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Post is not exist");
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

    @Test
    void shouldDeletePostById() {
        //given
        String id = "123456";

        //when
        postService.deletePost(id);

        //then
        verify(postRepository, times(1)).deleteById(id);

    }

    @Test
    void shouldUpdatePost() {
        //given
        String id = "abc123";

        Post existing = new Post();
        existing.setId(id);
        existing.setTitle("Old title");

        Post updatedPost = new Post();
        updatedPost.setTitle("New title");

        when(postRepository.findById(id)).thenReturn(Optional.of(existing));
        when(postRepository.save(existing)).thenReturn(existing);
        //when
        Post result = postService.updatePost(id, updatedPost);

        //then
assertThat(result.getTitle()).isEqualTo("New title");
verify(postRepository, times(1)).save(existing);
    }

    @Test
    void shouldAddComment() {
        //given
        Post post = new Post();
        post.setId("123");
        post.setTitle("New Post");

        Comment comment = new Comment();
        comment.setText("Good job!");

        when(postRepository.findById("123")).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        //when
        Post result = postService.addComment("123", comment);

        //then
        assertThat(result.getComments()).hasSize(1);
        assertThat(result.getComments().get(0).getText()).isEqualTo("Good job!");
    }

}
