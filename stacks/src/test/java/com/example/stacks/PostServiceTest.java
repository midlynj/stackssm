package com.example.stacks;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.repository.PostRepository;
import com.example.stacks.repository.UserRepository;
import com.example.stacks.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    PostServiceImpl service;
    @Mock
    PostRepository dao;
    @Mock
    UserRepository userRepository;

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
    void testFindAllPosts() {
        when(dao.findAll()).thenReturn(postList);

        List<Post> posts = service.fetchAllPosts();

        assertEquals(2, posts.size());
        assertEquals(1, posts.get(0).getId());
        assertEquals(2, posts.get(1).getId());
        assertEquals("a", posts.get(0).getAuthor().getFirstName());
        assertEquals("user2firstname", posts.get(1).getAuthor().getFirstName());
        assertEquals("first post", posts.get(0).getContent());
        assertEquals("second post", posts.get(1).getContent());

        verify(dao, times(1)).findAll();
    }

    @Test
    void testFindPostById() {
        when(dao.findById(1L)).thenReturn(Optional.ofNullable(postList.get(0)));

        Post post = service.fetchPostById(1L);

        assertEquals("first post", post.getContent());
        assertEquals("a", post.getAuthor().getFirstName());
        assertEquals(postList.get(0), post);

        verify(dao, times(1)).findById(1L);
    }

    @Test
    void testCreatePost() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(postList.get(0).getAuthor()));

        PostDto postDto = new PostDto("3rd post", LocalDate.now());
        service.createPost(1L, postDto);

        // Argument captor to capture the post object passed to the save method
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(dao, times(1)).save(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();

        assertEquals("3rd post", capturedPost.getContent());
        assertEquals(postList.get(0).getAuthor(), capturedPost.getAuthor());
    }
}
