package com.christabelj.blog.mapper;

import com.christabelj.blog.domain.dto.request.CreatePostRequest;
import com.christabelj.blog.domain.dto.request.UpdatePostRequest;
import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.domain.entity.Post;

public interface PostMapper {

    // take incoming request DTO --> Post entity in database
    Post fromRequestDto(CreatePostRequest request);

    Post fromUpdateRequestDto(UpdatePostRequest request);

    // Post entity from database --> Post Response dto
    PostResponse toResponseDto(Post post);
}
