package com.fashion_blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
@Builder


public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ElementCollection
    private Set<Long> likes;
}
