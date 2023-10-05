package com.example.stacks.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate createdAt;

    @ManyToOne
    @JsonIgnoreProperties({"posts", "password"})
    private User author;


//    @JoinTable(
//            name="post_category",
//            joinColumns = {@JoinColumn(name = "post_id", nullable = false, updatable = false)},
//            inverseJoinColumns = {@JoinColumn(name="category_id", nullable = false, updatable = false)},
//            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
//            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
//    )


}
