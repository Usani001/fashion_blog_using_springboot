package com.fashion_blog.controllers.user_controllers;



import com.fashion_blog.dtos.CommentDTO;
import com.fashion_blog.entity.Comment;
import com.fashion_blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/newComment/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId ,@RequestBody CommentDTO request) {
        CommentDTO response = commentService.createComment(postId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("updateComment/{userId}/{postId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody CommentDTO request
    ) {
        CommentDTO response = commentService.updateComment(userId, postId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentsOfPost(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        List<Comment> comments = commentService.getAllCommentsOfPost(userId, postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentDTO> getComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDTO comment
    ) {
        CommentDTO response = commentService.getComment(postId, commentId, comment);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}/{commentId}/like")
    public ResponseEntity<?> likeComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        var likeCount = commentService.likeComment(postId, commentId);
        return ResponseEntity.ok(likeCount);
    }

    @PostMapping("/{postId}/{commentId}/unlike")
    public ResponseEntity<?> unlikeComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        var likeCount = commentService.unlikeComment(postId, commentId);
        return ResponseEntity.ok(likeCount);
    }
}
