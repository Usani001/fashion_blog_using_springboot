package com.fashion_blog.services;

import com.fashion_blog.dtos.CommentDTO;
import com.fashion_blog.dtos.response.PostResponseDTO;
import com.fashion_blog.entity.Comment;
import com.fashion_blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentDTO createComment(Long postId, CommentDTO request);

    CommentDTO updateComment(Long userId, Long postId, CommentDTO request);

    List<Comment> getAllCommentsOfPost(Long userId, Long postId);

    CommentDTO getComment(Long postId, Long commentId, CommentDTO comment);

    PostResponseDTO likeComment(Long postId, Long commentId);

    PostResponseDTO unlikeComment(Long postId, Long commentId);
}
