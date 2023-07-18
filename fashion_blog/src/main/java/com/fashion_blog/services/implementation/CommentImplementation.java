package com.fashion_blog.services.implementation;

import com.fashion_blog.dtos.CommentDTO;
import com.fashion_blog.dtos.response.PostResponseDTO;
import com.fashion_blog.entity.Comment;
import com.fashion_blog.entity.Post;
import com.fashion_blog.repository.CommentRepository;
import com.fashion_blog.repository.PostRepository;
import com.fashion_blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentImplementation implements CommentService {
        private final CommentRepository commentRepository;
        private final PostRepository postRepository;

        @Override
        public CommentDTO createComment(Long postId, CommentDTO request) {
                Comment comment = new Comment();
                comment.setContent(request.getContent());

                Optional<Post> postOptional = postRepository.findById(postId);
                if (postOptional.isEmpty()) {
                        throw new NoSuchElementException("Post not found with ID: " + postId);
                }

                Post post = postOptional.get();
                comment.setPost(post);

                post.addComment(comment);

                commentRepository.save(comment);

                // Map the created comment to DTO
                CommentDTO response = CommentDTO.builder()
                        .appUser(request.getAppUser())
                        .post(post)
                        .content(comment.getContent())
                        .likes(comment.getLikes())
                        .build();


                return response;
        }

        @Override
        public CommentDTO updateComment(Long userId, Long postId, CommentDTO request) {
                Optional<Comment> commentOptional = commentRepository.findByIdAndPostId(postId, request.getId());
                if (commentOptional.isEmpty()) {
                        throw new NoSuchElementException("Comment not found with ID: " + request.getId());
                }

                Comment comment = commentOptional.get();
                comment.setContent(request.getContent());

                commentRepository.save(comment);

                // Map the updated comment to DTO
                CommentDTO response = CommentDTO.builder()

                        .id(comment.getId())
                        .content(comment.getContent())
                        .post(comment.getPost())
                        .appUser(comment.getAppUser())
                        .build();

                return response;
        }

        @Override
        public List<Comment> getAllCommentsOfPost(Long userId, Long postId) {
                List<Comment> comments = commentRepository.findAllByPostId(postId);
                return comments;
        }

        @Override
        public CommentDTO getComment(Long postId, Long commentId, CommentDTO comment) {
                commentRepository.findByIdAndPostId(postId, commentId);
                return CommentDTO.builder()
                        .appUser(comment.getAppUser())
                        .content(comment.getContent())
                        .post(comment.getPost())
                        .build();
        }

        @Override
        public PostResponseDTO likeComment(Long postId, Long commentId) {
                Optional<Comment> commentOptional = commentRepository.findById(commentId);
                if (commentOptional.isPresent()) {
                        Comment comment1 = commentOptional.get();
                        if (!comment1.getLikes().contains(postId)) {
                                comment1.getLikes().add(postId);
                                commentRepository.save(comment1);
                        }

                        Post post = comment1.getPost();
                        return PostResponseDTO.builder()
                                .title(post.getTitle())
                                .imageUrl(post.getImageUrl())
                                .textContent(post.getTextContent())
                                .postOwner(post.getAppUser().getUserName() + " posted this")
                                .createdAt(post.getCreatedAt())
                                .comments(post.getComments().stream()
                                        .map(comment -> comment.getContent() + " - " + comment.getLikes().size() + " likes")
                                        .collect(Collectors.toList()))
                                .updatedAt(post.getUpdatedAt())
                                .likes(post.getLikes().size() + " likes")
                                .build();
                } else {
                        throw new NoSuchElementException("Comment not found with ID: " + commentId);
                }


        }

        @Override
        public PostResponseDTO unlikeComment(Long postId, Long commentId) {
                Optional<Comment> commentOptional = commentRepository.findById(commentId);
                if (commentOptional.isPresent()) {
                        Comment comment1 = commentOptional.get();
                        if (comment1.getLikes().contains(postId)) {
                                comment1.getLikes().remove(postId);
                                commentRepository.save(comment1);
                        }

                        Post post = comment1.getPost();
                        return PostResponseDTO.builder()
                                .title(post.getTitle())
                                .imageUrl(post.getImageUrl())
                                .textContent(post.getTextContent())
                                .postOwner(post.getAppUser().getUserName() + " posted this")
                                .createdAt(post.getCreatedAt())
                                .comments(post.getComments().stream()
                                        .map(comment -> comment.getContent() + " - " + comment.getLikes().size() + " likes")
                                        .collect(Collectors.toList()))
                                .updatedAt(post.getUpdatedAt())
                                .likes(post.getLikes().size() + " likes")
                                .build();
                } else {
                        throw new NoSuchElementException("Comment not found with ID: " + commentId);
                }
        }
}
