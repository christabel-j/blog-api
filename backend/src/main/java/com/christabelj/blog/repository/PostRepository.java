package com.christabelj.blog.repository;

import com.christabelj.blog.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// repository for handling posts
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
