package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.dto.UserDto2;
import com.example.stacks.entity.User;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//Use @SpringBootTest for tests that cover the whole Spring Boot application from incoming request to database.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @Sql("/m.sql")
    public void testAllUsers() {
        ResponseEntity<List<UserDto>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/users/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDto>>() {});

        List<UserDto> users = response.getBody();
        assertEquals(2,users.size());
        assertEquals("Joe", users.get(1).getFirstName());
        assertEquals("Danie", users.get(0).getFirstName());
    }

    @Test
//    @Sql("/m.sql")
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

        assertEquals(3, users.size());
        assertEquals("joey@email.com",users.get(2).getEmail());
    }

    @Test
//    @Sql("/m.sql")
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
//    @Sql("/m.sql")
    public void testSignIn() {
            SignIn signIn = new SignIn("joe@email.com","123");
            when(passwordEncoder.matches("123", signIn.getPassword())).thenReturn(true); // Stub password encoder behavior

            HttpEntity<SignIn> requestEntity = new HttpEntity<>(signIn);

            ResponseEntity<UserDto2> response = restTemplate.exchange(
                    "http://localhost:" + port + "/api/users/signin",
                    HttpMethod.POST,
                    requestEntity,
                    UserDto2.class

                    );
                     System.out.println(response);
                     assertEquals("joe@email.com", response.getBody().getEmail());
                     assertEquals("Joe", response.getBody().getFirstName());
        }


//alternative to testAllUsers method
//    @Test
//    @Sql(statements = {
//            "INSERT INTO user (first_name,last_name,email,password,user_picture, status) VALUES ('Danie','Yockney','dyo@email.com','123', null, 'ACTIVE')",
//            "INSERT INTO user (first_name,last_name,email,password,user_picture, status) VALUES ('Joe','Sta','joe@email.com','123', null, 'ACTIVE')"
//    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

}
