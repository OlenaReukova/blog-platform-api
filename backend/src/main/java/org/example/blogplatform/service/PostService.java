package org.example.blogplatform.service;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost (Post post ) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(String id, Post updatedPost) {
        Post existing = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        existing.setTitle(updatedPost.getTitle());
        existing.setContent(updatedPost.getContent());
        existing.setAuthor(updatedPost.getAuthor());
        existing.setTags(updatedPost.getTags());
        return postRepository.save(existing);
    }
}
