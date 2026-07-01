package com.christabelj.blog.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotBlank
        @Size(max = 5000)
        String content) {
}
