package com.example.stacks.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] data;

    @Lob
    private Blob userPicture;

//    @JoinColumn(name = "user_id")
//    @ManyToOne
//    @JsonIgnoreProperties("author")
//    private User author;
}
