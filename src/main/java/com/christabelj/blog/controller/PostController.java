package com.christabelj.blog.controller;


import com.christabelj.blog.domain.entity.Post;
import com.christabelj.blog.service.PostService;
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
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // get post by id
    @GetMapping(path = "/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // update post
    @PutMapping(path = "/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable UUID postId, @RequestBody Post post){
        return ResponseEntity.ok(postService.updatePost(postId, post));
    }

    // delete post
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
