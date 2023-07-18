package com.fashion_blog.repository;

import com.fashion_blog.entity.Comment;
import com.fashion_blog.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findByIdAndPostId(Long postId, Long commentId);
}
