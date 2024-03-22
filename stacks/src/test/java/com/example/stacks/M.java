package com.example.stacks;

import com.example.stacks.controller.UserController;
import com.example.stacks.dto.UserDto;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class M {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;
    @Mock
    private UserServiceImpl service;

    List<UserDto> userDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        UserDto userDto = new UserDto(1L,"j","s","j@email.com",null);
        userDtoList.add(userDto);
    }

    @Test
    public void testGetAllUsers() throws Exception {
       when(service.fetchAllUsers()).thenReturn(userDtoList);
        this.mockMvc.perform(get("/api/users/all")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(userDtoList.size())) // Assert that the JSON array has the same length as userDtoList
                .andExpect(jsonPath("$[0].id").value(userDtoList.get(0).getId())) // Assert that the first user's ID in the response matches the ID of the first user in userDtoList
                .andExpect(jsonPath("$[0].firstName").value(userDtoList.get(0).getFirstName())) // Assert that the first user's first name in the response matches the first name of the first user in userDtoList
                .andExpect(jsonPath("$[0].lastName").value("s"))

                .andReturn();
    }
}
