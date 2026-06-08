package com.christabelj.blog.service;

import com.christabelj.blog.domain.entity.Post;
import com.christabelj.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(UUID id){
        return postRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(UUID id, Post post){
        Post toUpdate = postRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Post not found"));
        toUpdate.setTitle(post.getTitle());
        toUpdate.setContent(post.getContent());
        return postRepository.save(toUpdate);
    }

    @Override
    public void deletePost(UUID id){
        postRepository.deleteById(id);
    }

}
