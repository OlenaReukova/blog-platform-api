package org.example.blogplatform.service;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.repository.PostRepository;
import org.springframework.stereotype.Service;

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
}
