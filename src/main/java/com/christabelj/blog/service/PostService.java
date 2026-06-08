package com.christabelj.blog.service;

// service for handling blog posts

import com.christabelj.blog.domain.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    // create a blog post
    Post createPost(Post post);

    // get blog post by id
    Post getPostById(UUID id);

    // get all posts
    List<Post> getAllPosts();

    // update a post
    Post updatePost(UUID id, Post post);

    // delete a post
    void deletePost(UUID id);

}
