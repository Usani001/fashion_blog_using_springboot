package com.fashion_blog.dtos.request;

import com.fashion_blog.entity.AppUser;
import com.fashion_blog.entity.Comment;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class PostRequestDTO {
    private String title;

    private String imageUrl;


    private String textContent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private  AppUser appUser;

    private List<Comment> comments;
    @ElementCollection
    private Set<Long> likes;
}
