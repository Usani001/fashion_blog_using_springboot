package com.fashion_blog.controllers.user_controllers;

import com.fashion_blog.dtos.request.AppUserRequestDTO;
import com.fashion_blog.dtos.request.PostRequestDTO;
import com.fashion_blog.dtos.response.PostResponseDTO;
import com.fashion_blog.entity.Post;
import com.fashion_blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;


    @PostMapping("/createPost/{userId}")
    public ResponseEntity<?> createPost(@PathVariable Long userId, @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.createPost(userId ,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllPosts/{userId}")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getAllPost(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/getPost/{userId}/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long userId, @PathVariable Long postId) {
        PostResponseDTO post = postService.getPost(userId, postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/updatePost/{userId}/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.updatePost(userId, postId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletePost/{userId}/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long userId,@PathVariable Long postId) {
            String message = postService.deletePost(userId, postId);
            return ResponseEntity.ok(message);

    }

    @PostMapping("/{userId}/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        var totalLikes = postService.likePost(userId, postId);
        return ResponseEntity.ok(totalLikes);
    }

    @PostMapping("/{userId}/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        var totalLikes = postService.unlikePost(userId, postId);
        return ResponseEntity.ok(totalLikes);
    }
}
