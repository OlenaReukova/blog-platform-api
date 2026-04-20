package org.example.blogplatform.repository;

import org.example.blogplatform.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findByAuthor(String author);
    List<Post> findByTagsContaining(String tag);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
