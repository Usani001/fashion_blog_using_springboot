package com.fashion_blog.dtos;

import com.fashion_blog.entity.AppUser;
import com.fashion_blog.entity.Post;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    public Long id;

    private String content;


    private AppUser appUser;


    private Post post;

    private Set<Long> likes;
}
