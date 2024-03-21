package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.User;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.Signup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

//Use @SpringBootTest for tests that cover the whole Spring Boot application from incoming request to database.

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    @Sql("/m.sql")
//    public void testAllUsers() {
//        ResponseEntity<List<UserDto>> response = restTemplate.exchange(
//                "http://localhost:" + port + "/api/users/all",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<UserDto>>() {});
//
//        List<UserDto> users = response.getBody();
//        assertEquals(2,users.size());
//        assertEquals("Joe", users.get(1).getFirstName());
//        assertEquals("Danie", users.get(0).getFirstName());
//    }

 @Test
// @Sql("/n.sql")
    public void testCreateUser() {
        Signup newUser = new Signup("Joey", "Doe","joey@email.com","123",null);

        HttpEntity<Signup> requestEntity = new HttpEntity<>(newUser);

        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/users/create",
                HttpMethod.POST,
                requestEntity,
                MessageResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        MessageResponse response = responseEntity.getBody();

        assertEquals("New user registered successfully", response.getMessage() );

        ResponseEntity<List<UserDto>> getUsersResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/users/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>() {});

        List<UserDto> users = getUsersResponse.getBody();
        assertNotNull(users);
     System.out.println(users);
//
//        assertEquals(3, users.size());
//        assertEquals("joey@email.com",users.get(2).getEmail());
    }

    @Test
    @Sql("/m.sql")
    public void testById() {
        long userId = 1L;
        ResponseEntity<User> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/users/{id}",
                HttpMethod.GET,
                null,
                User.class,
                userId);

        User user = response.getBody();
        assertEquals("Danie", user.getFirstName());
    }

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
//        integration test
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/users",
                String.class)).isEqualTo("Hello, World");
    }

    @Test
    void shouldReturnDefaultMessage() throws Exception {
//        isolation test
        this.mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

}
