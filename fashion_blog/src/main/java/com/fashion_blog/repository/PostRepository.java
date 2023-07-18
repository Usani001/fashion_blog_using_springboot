package com.fashion_blog.repository;

import com.fashion_blog.dtos.response.AppUserResponseDTO;
import com.fashion_blog.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAppUserId(Long userId);

    Optional<Post> findByAppUserIdAndId(Long userId,Long postId);


}
