package org.example.blogplatform.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    private String title;
    private String content;
    private String author;
    private List<String> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
}
