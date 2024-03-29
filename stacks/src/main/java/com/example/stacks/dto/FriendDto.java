package com.example.stacks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
