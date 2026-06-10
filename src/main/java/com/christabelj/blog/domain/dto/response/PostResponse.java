package com.christabelj.blog.domain.dto.response;

import java.util.UUID;
import java.time.Instant;

public record PostResponse(
        UUID id,
        String title,
        String content,
        Instant created,
        Instant updated
) {
}
