package org.example.blogplatform.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Comment {
    @Id
    private String id = UUID.randomUUID().toString();

    private String text;
    private String author;
    private LocalDateTime createdAt = LocalDateTime.now();
}
