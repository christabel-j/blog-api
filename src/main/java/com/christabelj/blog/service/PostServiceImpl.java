package com.christabelj.blog.service;

import com.christabelj.blog.domain.dto.request.CreatePostRequest;
import com.christabelj.blog.domain.dto.request.UpdatePostRequest;
import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.domain.entity.Post;
import com.christabelj.blog.mapper.PostMapper;
import com.christabelj.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // method flow: DTO → map to entity → database operation → map to response DTO → return

    @Override
    public PostResponse createPost(CreatePostRequest request){
        Post post = postMapper.fromRequestDto(request); // 1. map request DTO to entity
        Post savedPost = postRepository.save(post); // 2. save entity to database
        return postMapper.toResponseDto(savedPost); // 3. map saved entity to response DTO
    }

    @Override
    public PostResponse getPostById(UUID id){
        Post post = postRepository.findById(id) // find the post in the DB by id
                .orElseThrow(()-> new RuntimeException("Post not found"));

        return postMapper.toResponseDto(post); // convert entity to response dto and return it
    }

    @Override
    public List<PostResponse> getAllPosts(){
        // Get all Post entities from the database
        return postRepository.findAll()
                .stream() // converts list to stream
                .map(postMapper::toResponseDto) // each entity->response DTO. ```postMapper::toResponseDto``` is called a method reference
                .toList(); // back to list which is returned
    }

    @Override
    public PostResponse updatePost(UUID id, UpdatePostRequest request){
        Post toUpdate = postRepository.findById(id) // get the entity from the DB to update from the id given
                .orElseThrow(()-> new RuntimeException("Post not found")); // if not found, throw exception

        toUpdate.setTitle(request.title()); // change the title of existing entity to the one from the user's update request
        toUpdate.setContent(request.content()); // change the content of existing entity to the one from the user's update request

        Post savedPost = postRepository.save(toUpdate); // save the updated entity
        return postMapper.toResponseDto(savedPost); // map to response dto & return
    }

    @Override
    public void deletePost(UUID id){
        postRepository.deleteById(id); // find entity by id and delete
    }

}
