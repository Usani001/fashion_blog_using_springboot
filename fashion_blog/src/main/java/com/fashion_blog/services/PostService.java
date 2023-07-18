package com.fashion_blog.services;

import com.fashion_blog.dtos.request.AppUserRequestDTO;
import com.fashion_blog.dtos.request.PostRequestDTO;
import com.fashion_blog.dtos.response.PostResponseDTO;
import com.fashion_blog.entity.Post;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponseDTO createPost(Long userId, PostRequestDTO request);
    List<Post> getAllPost(Long userId);
    PostResponseDTO getPost(Long userId,Long postId);
    PostResponseDTO updatePost(Long userId,Long postId, PostRequestDTO request);
    String deletePost(Long userId,Long postId);

    PostResponseDTO likePost(Long userId, Long postId);

    PostResponseDTO unlikePost(Long userId, Long postId);

}
