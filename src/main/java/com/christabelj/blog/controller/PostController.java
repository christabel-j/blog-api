package com.christabelj.blog.controller;


import com.christabelj.blog.domain.dto.request.CreatePostRequest;
import com.christabelj.blog.domain.dto.request.UpdatePostRequest;
import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/posts")
public class PostController {
    private final PostService postService;

    // create post
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid CreatePostRequest request){
        PostResponse createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // get all posts
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // get post by id
    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // update post
    @PutMapping(path = "/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID postId, @Valid @RequestBody UpdatePostRequest request){
        PostResponse response = postService.updatePost(postId, request);
        return ResponseEntity.ok(response);
    }

    // delete post
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
