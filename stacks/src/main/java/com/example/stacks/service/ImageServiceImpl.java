package com.example.stacks.service;

import com.example.stacks.entity.Image;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.ImageInterface;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageInterface {
    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final UserRepository userRepository;

    public static boolean areBytesEqual(byte[] array1, byte[] array2) {
        if (array1 == null || array2 == null)
            return false; // If either array is null, they are not equal.

        if (array1.length != array2.length)
            return false; // If the arrays have different lengths, they are not equal.

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i])
                return false; // If any byte is different, the arrays are not equal.
        }

        return true; // If all bytes are the same, the arrays are equal.
    }

    @Override
    public ResponseEntity<?> uploadImage(MultipartFile file, Image image) throws SQLException, java.io.IOException {
        byte[] bytes = file.getBytes();

        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        image.setUserDefault(blob);
        imageRepository.save(image);

        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> displayImage(Long userId) throws SQLException {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Image> imageOptional = imageRepository.findById(1L);

        if (userOptional.isEmpty())
            return ResponseEntity.ok(new MessageResponse("User does not exist"));

        User userExist = userOptional.get();
        Image defaultImage = imageOptional.get();

        Blob userPicture = userExist.getUserPicture();
        Blob defaultImageUserDefault = defaultImage.getUserDefault();

        byte[] imageBytes1 = userPicture.getBytes(1, (int) userPicture.length());
        byte[] imageBytes2 = defaultImageUserDefault.getBytes(1, (int) defaultImageUserDefault.length());

        boolean areImagesEqual = areBytesEqual(imageBytes1, imageBytes2);

        if (!areImagesEqual) {
            System.out.println("user picture");

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes1);

        } else {
            System.out.println("default picture");

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes2);
        }

    }

    @Override
    public ResponseEntity<?> updateImage(MultipartFile file, @PathVariable Long userId) throws SQLException, java.io.IOException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            return ResponseEntity.ok(new MessageResponse("User does not exist"));

        User user = userOptional.get();

        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        user.setUserPicture(blob);
        userRepository.save(user);

        return ResponseEntity.ok("Success");
    }
}
