package com.fashion_blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
@Builder
public class AppUser {


        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;
        @Column(length = 30)
        private String userName;

        @Column(length = 30)
        private String email;

        @Column(length = 30)
        private String password;

        @JsonIgnore
        @ToString.Exclude
        @OneToMany(mappedBy = "appUser",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<Post> posts = new ArrayList<>();

        @JsonIgnore
        @ToString.Exclude
        @OneToMany(mappedBy = "appUser",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<Comment> comments = new ArrayList<>();

        public void addPost(Post post) {
                if (this.posts.size()==0){
                        posts.add(post);
                }
        }

}
