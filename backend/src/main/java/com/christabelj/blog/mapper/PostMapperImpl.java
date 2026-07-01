package com.christabelj.blog.mapper;

import com.christabelj.blog.domain.dto.request.CreatePostRequest;
import com.christabelj.blog.domain.dto.request.UpdatePostRequest;
import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.domain.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapperImpl implements PostMapper {
    @Override
    public Post fromRequestDto(CreatePostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }

    @Override
    public Post fromUpdateRequestDto(UpdatePostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }

    @Override
    public PostResponse toResponseDto(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreated(),
                post.getUpdated()
        );
    }
}
