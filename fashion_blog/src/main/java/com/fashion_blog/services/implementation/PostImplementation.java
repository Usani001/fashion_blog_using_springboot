package com.fashion_blog.services.implementation;


import com.fashion_blog.dtos.request.PostRequestDTO;
import com.fashion_blog.dtos.response.PostResponseDTO;
import com.fashion_blog.entity.AppUser;
import com.fashion_blog.entity.Post;
import com.fashion_blog.repository.AppUserRepository;
import com.fashion_blog.repository.PostRepository;
import com.fashion_blog.services.PostService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Setter
@Getter
@RequiredArgsConstructor
public class PostImplementation implements PostService {
    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public PostResponseDTO createPost(Long userId,PostRequestDTO request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .textContent(request.getTextContent())
                .createdAt(LocalDateTime.now())
                .build();


        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        AppUser appUser = optionalAppUser.get();
        post.setAppUser(appUser);

        appUser.addPost(post);
        postRepository.save(post);


        return PostResponseDTO.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .textContent(request.getTextContent())
                .createdAt(post.getCreatedAt())
                .postOwner(post.getAppUser().getUserName()+ " just made a post")
//                .comments(List.of(post.getComments()))

                .build();
    }

    @Override
    public List<Post> getAllPost(Long userId) {
        return postRepository.findAllByAppUserId(userId);
    }

    @Override
    public PostResponseDTO getPost(Long userId, Long postId) {
        // Retrieve the post from the repository
        Post post = postRepository.findByAppUserIdAndId(userId, postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        // Create the response DTO and populate it with the post data

        PostResponseDTO response = PostResponseDTO.builder()
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .textContent(post.getTextContent())
                .createdAt(post.getCreatedAt())
                .likes(post.getLikes().size() + " likes")
                .postOwner(post.getAppUser().getUserName())
                .comments(post.getComments().stream()
                        .map(comment -> comment.getContent() + " - "
                        + comment.getLikes().size() + " likes")
                        .collect(Collectors.toList()))
                .build();


        return response;
    }


    @Override
    public PostResponseDTO updatePost(Long userId,Long postId, PostRequestDTO request) {
        Optional<Post> optionalPost =postRepository.findByAppUserIdAndId(userId, postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
                    post.setTitle(request.getTitle());
                    post.setImageUrl(request.getImageUrl());
                    post.setTextContent(request.getTextContent());
                    post.setUpdatedAt(LocalDateTime.now());
                    postRepository.save(post);


            PostResponseDTO response = PostResponseDTO.builder()
                    .title(request.getTitle())
                    .imageUrl(request.getImageUrl())
                    .textContent(request.getTextContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .postOwner(post.getAppUser().getUserName())
                    .comments(post.getComments().stream()
                            .map(comment -> comment.getContent() + " - "
                                    + comment.getLikes().size() + " likes")
                            .collect(Collectors.toList()))
                    .likes(post.getLikes().size()+" likes")
                    .build();
            log.info("Post has been updated successfully");

            return response;
        }else{
            throw new NoSuchElementException("Post not found with ID: " + postId);
        }


    }

    @Override
    public String deletePost(Long userId,Long postId) {
        postRepository.deleteById(postId);
        return "Post with "+postId+" has been deleted";

    }

    @Override
    public PostResponseDTO likePost(Long userId, Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (!post.getLikes().contains(userId)) {
                post.getLikes().add(userId);
                postRepository.save(post);

            }
            log.info("User with ID: {} just liked a post with ID: {} ", userId, postId);


            return PostResponseDTO.builder()
                    .title(post.getTitle())
                    .imageUrl(post.getImageUrl())
                    .textContent(post.getTextContent())
                    .postOwner(post.getAppUser().getUserName()+ " posted this")
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .comments(post.getComments().stream()
                            .map(comment -> comment.getContent() + " - "
                                    + comment.getLikes().size() + " likes")
                            .collect(Collectors.toList()))
                    .likes(post.getLikes().size()+" likes")
                    .build();

        } else {
            throw new NoSuchElementException("Post not found with post ID: " + postId);
        }
    }

    @Override
    public PostResponseDTO unlikePost(Long userId, Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getLikes().contains(userId)) {
                post.getLikes().remove(userId);
                postRepository.save(post);

            }
            log.info("User with ID: "+userId+" just unliked a post with post ID: "+postId);

            return PostResponseDTO.builder()
                    .title(post.getTitle())
                    .imageUrl(post.getImageUrl())
                    .textContent(post.getTextContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .likes(post.getLikes().size()+" likes")
                    .build();


        } else {
            throw new NoSuchElementException("Post not found with ID: " + postId);
        }
    }


}
