package com.example.stacks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIntegrationTest {
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PostTestRepo postTestRepo;

    @Test
    // @Sql("/o.sql")
    public void testFetchAllPost() {
        ResponseEntity<List<Post>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/posts/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Post>>() {});
                
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(4, response.getBody().size());
                assertEquals("Danie first post", response.getBody().get(0).getContent());
                assertEquals("Danie second post", response.getBody().get(1).getContent());
                assertEquals("Joe 3rd post", response.getBody().get(2).getContent());

                System.out.println(response.getBody());
    }

    @Test
    @Sql("/o.sql")
    public void testFetchPostById() {
        long postId = 1L;
        ResponseEntity<Post> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/posts/{id}",
                HttpMethod.GET,
                null,
                Post.class,
                postId);
                assertEquals("Danie first post", response.getBody().getContent());
                assertEquals(1, response.getBody().getId());
    }

    @Test
    // @Sql("/o.sql")
    public void testCreatePost() {
        long authorId = 3L;
        PostDto postDto = new PostDto("Ki 1st post", LocalDate.now());
        HttpEntity<PostDto> requestEntity = new HttpEntity<>(postDto);

        restTemplate.exchange(
                "http://localhost:" + port + "/api/posts/{id}/new-post",
                HttpMethod.POST,
                requestEntity,
                Void.class,
                authorId);

                assertEquals(4, postTestRepo.findAll().size());
                assertEquals("Ki", postTestRepo.findAll().get(3).getAuthor().getFirstName());
                assertEquals("Ki 1st post", postTestRepo.findAll().get(3).getContent());

                System.out.println(postTestRepo.findAll().size());
    }
    
}
