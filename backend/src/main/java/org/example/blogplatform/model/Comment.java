package org.example.blogplatform.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Comment {
    @Id
    private String id;

    private String text;
    private String author;
    private LocalDateTime createdAt = LocalDateTime.now();
}
