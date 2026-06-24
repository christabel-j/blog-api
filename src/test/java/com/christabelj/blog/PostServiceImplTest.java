package com.christabelj.blog;

import com.christabelj.blog.mapper.PostMapper;
import com.christabelj.blog.repository.PostRepository;
import com.christabelj.blog.service.PostServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
}
