package com.fashion_blog.entity;
//package com.fashion_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
@Builder

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    @Column(length = 2000)
    private String imageUrl;

    private String textContent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    private Set<Long> likes;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private  AppUser appUser;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public void addComment(Comment comment) {
        if (this.comments.size()==0){
            comments.add(comment);
        }
    }


}
