package com.example.stacks.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePost {
    private Long id;
    private String content;
    private LocalDate createdAt;
}
