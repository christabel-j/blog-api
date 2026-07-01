package com.christabelj.blog.service;

// service for handling blog posts

import com.christabelj.blog.domain.dto.request.CreatePostRequest;
import com.christabelj.blog.domain.dto.request.UpdatePostRequest;
import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.domain.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    // create a blog post
    PostResponse createPost(CreatePostRequest request);

    // get blog post by id
    PostResponse getPostById(UUID id);

    // get all posts
    List<PostResponse> getAllPosts();

    // update a post
    PostResponse updatePost(UUID id, UpdatePostRequest request);

    // delete a post
    void deletePost(UUID id);

}
