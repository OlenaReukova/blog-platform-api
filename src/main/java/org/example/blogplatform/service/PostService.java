package org.example.blogplatform.service;

import org.example.blogplatform.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
