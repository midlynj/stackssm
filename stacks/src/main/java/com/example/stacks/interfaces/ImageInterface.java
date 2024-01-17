package com.example.stacks.interfaces;

import com.example.stacks.entity.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface ImageInterface {
    void uploadImage(MultipartFile file, Image image) throws SQLException, IOException;
    ResponseEntity<?> displayImage(Long userId) throws SQLException;
    ResponseEntity<?> getImageById(Long imageId) throws SQLException;
    void updateImage(MultipartFile file, @PathVariable Long userId) throws SQLException, IOException;
}
