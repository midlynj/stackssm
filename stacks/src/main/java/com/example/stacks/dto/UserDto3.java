package com.example.stacks.dto;

import com.example.stacks.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto3 {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Status status;
}
