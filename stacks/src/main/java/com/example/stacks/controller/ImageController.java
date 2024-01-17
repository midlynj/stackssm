package com.example.stacks.controller;

import com.example.stacks.entity.Image;
import com.example.stacks.service.ImageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/image", headers = "Accept=application/json")
public class ImageController {
    private final ImageServiceImpl imageService;

    @PostMapping("/upload")
    public void uploadImage(@RequestParam("file") MultipartFile file, Image image) throws SQLException, java.io.IOException {
         imageService.uploadImage(file, image);
    }

    @GetMapping("/display/{userId}")
    public ResponseEntity<?> displayImage(@PathVariable Long userId) throws SQLException {
        return imageService.displayImage(userId);
    }

    @GetMapping("/getImage/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) throws SQLException {
        return imageService.getImageById(imageId);
    }

    @PutMapping("/update/{userId}")
    public void updateImage(@RequestParam MultipartFile file, @PathVariable Long userId) throws SQLException, java.io.IOException {
         imageService.updateImage(file, userId);
    }
}
