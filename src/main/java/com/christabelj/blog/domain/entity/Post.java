// represents a blog post in the posts table in the H2 DB

package com.christabelj.blog.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

// @Entity annotation to mark this as a JPA entity. This class maps to a database table
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    // The post's unique identifier. Generated automatically by JPA
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;


    // title of post. max 255 characters
    @Column(name = "title", nullable = false)
    private String title;

    // post's content
    @Column(name = "content", nullable = false, length=1600)
    private String content;

    // date and time of blog post creation
    @Column(name = "created", updatable = false, nullable = false)
    private Instant created;

    // date and time of when the blog post was updated
    @Column(name = "updated")
    private Instant updated;

}
