package org.example.blogplatform.controller;

import org.example.blogplatform.model.Post;
import org.example.blogplatform.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }
@PostMapping
    public ResponseEntity<Post>createPost(@RequestBody Post post) {
        Post created = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
}
