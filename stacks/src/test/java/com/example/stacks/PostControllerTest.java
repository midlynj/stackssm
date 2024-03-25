package com.example.stacks;

import com.example.stacks.controller.PostController;
import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @InjectMocks
    private PostController postController;
    @Mock
    private PostServiceImpl postService;

    List<Post> postList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        User user = new User(1L, "a", "s", "a@email.com", "123", null, null, null, Status.ACTIVE, null);

        User user2 = new User(2L, "user2firstname", "user2lastname", "user2@email.com", "123", null, null, null, Status.ACTIVE, null);

        Post post1 = new Post(1L, "first post", LocalDate.now(), user);
        Post post2 = new Post(2L, "second post", LocalDate.now(), user2);
        postList.add(post1);
        postList.add(post2);
    }

    @Test
    void testFetchAllPost() {
        when(postService.fetchAllPosts()).thenReturn(postList);
        List<Post> posts = postController.fetchPosts();
        assertEquals("first post", posts.get(0).getContent());
        assertEquals(2, posts.size());
        verify(postService, times(1)).fetchAllPosts();
    }

    @Test
    void testPostById() {
        when(postService.fetchPostById(1L)).thenReturn(postList.get(0));
        Post post = postController.fetchPostById(1L);
        assertEquals("first post", post.getContent());
        verify(postService, times(1)).fetchPostById(1L);
    }

    @Test
    void testCreatePost() {
        PostDto postDto = new PostDto("3rd post", LocalDate.now());
        postController.createPost(1L, (postDto));

        verify(postService, times(1)).createPost(1L, postDto);
    }
}
