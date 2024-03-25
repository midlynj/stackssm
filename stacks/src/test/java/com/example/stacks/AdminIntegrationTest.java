package com.example.stacks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.example.stacks.dto.UserDto3;
import com.example.stacks.entity.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminIntegrationTest {
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    AdminTestRepo testRepo;

    // @Test
    // @Sql("/n.sql")
    // public void testAllUsers() {
    //     String adminEmail = "dyol@email.com";
    //     ResponseEntity<List<UserDto3>> response = restTemplate.exchange(
    //             "http://localhost:" + port + "/api/admin/all/users?email=" + adminEmail,
    //             HttpMethod.GET,
    //             null,
    //             new ParameterizedTypeReference<List<UserDto3>>() {});
                
    //             assertEquals(HttpStatus.OK, response.getStatusCode());
    //             assertEquals(2, response.getBody().size());
    //             assertEquals("Joel", response.getBody().get(0).getFirstName());
    //             assertEquals("Kairol", response.getBody().get(1).getFirstName());
    //             System.out.println(response.getBody().size());
    // }

    @Test
    public void testRestrictUserById() {
        long userId = 2L;
    
        restTemplate.exchange(
                "http://localhost:" + port + "/api/admin/restrict/{id}",
                HttpMethod.PUT,
                null,
                Void.class,
                userId);

                User user = testRepo.findById(userId).orElse(null);                
                assertEquals("RESTRICTED", user.getStatus().toString());
    }

    @Test
    public void testActivateUserById() {
        long userId = 3L;
    
        restTemplate.exchange(
                "http://localhost:" + port + "/api/admin/activate/{id}",
                HttpMethod.PUT,
                null,
                Void.class,
                userId);

                User user = testRepo.findById(userId).orElse(null);                
                assertEquals("ACTIVE", user.getStatus().toString());
    }

    @Test
    public void testDeleteUserById() {
        long userId = 2L;
    
        restTemplate.exchange(
                "http://localhost:" + port + "/api/admin/delete/{id}",
                HttpMethod.PUT,
                null,
                Void.class,
                userId);

                User user = testRepo.findById(userId).orElse(null);                
                assertEquals("INACTIVE", user.getStatus().toString());
    } 
    
}
