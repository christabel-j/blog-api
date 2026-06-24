package com.christabelj.blog;

import com.christabelj.blog.domain.dto.response.PostResponse;
import com.christabelj.blog.domain.entity.Post;
import com.christabelj.blog.mapper.PostMapper;
import com.christabelj.blog.repository.PostRepository;
import com.christabelj.blog.service.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


// unit test class -> tests one class in complete isolation, with all dependencies replaced by mocks

@ExtendWith(MockitoExtension.class) // tell JUnit to use Mockito, without this @Mock and @InjectMock don't work
public class PostServiceImplTest {

    // Setup (Test Fixture) - @Mock & @InjectMocks

    // @Mock - fake dependencies you control the outcome. we don't want to use the real repository and mapper just for tests
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;


    // creates a real instance of PostServiceImpl — the actual class you want to test and injects mocks
    @InjectMocks
    private PostServiceImpl postService; // this is a real PostServiceImpl


    // TEST METHODS
    @Test
    void shouldReturnAllPosts(){
        // 1) Arrange
        // fake test data (Posts) that the repository will pretend to return
            // List.of - quick way to create a list
            // no need for Post fields yet
        List<Post> posts = List.of(
                new Post(),
                new Post()
        );

        // tell mock repository "when findAll() is called, return this list." This is called stubbing. only needed when we are calling mock dependencies
        when(postRepository.findAll()).thenReturn(posts);

        // stub the mapper - needed because getAllPosts() calls toResponseDto() on each post
        // any(Post.class) = instead of specific object variable name which we didn't give, we need this to match ANY Post object passed in
        when(postMapper.toResponseDto(any(Post.class))).thenReturn(new PostResponse(null, null, null, null, null));

        // 2) Act - always call the method you are testing on @InjectMocks (the real service), never on a @Mock; store the result
        List<PostResponse> result = postService.getAllPosts();

        // 3) Assert
        assertNotNull(result);
        assertEquals(2, result.size());

    }


}