package com.fashion_blog.dtos.response;

import com.fashion_blog.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class PostResponseDTO {
    private String title;

    private String imageUrl;

    private String textContent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private  String postOwner;

    private List<String> comments;

    private String likes;
}
