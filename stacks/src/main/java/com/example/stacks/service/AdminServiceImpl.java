package com.example.stacks.service;

import com.example.stacks.component.DtoMapper;
import com.example.stacks.dto.UserDto3;
import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.AdminInterface;
import com.example.stacks.payload.AdminAccess;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.repository.RoleRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminInterface {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail) {
        Optional<User> userOptional = userRepository.findUserByEmail(adminEmail.getEmail());

       if (userOptional.isEmpty()) {
           LOGGER.error("Email not found to access resource");
           throw new RuntimeException("Email not found");
       }

       User userExist = userOptional.get();

       Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));

       if (!userExist.getRoles().contains(adminRole)) {
           LOGGER.error("Unauthorized user attempting to access resource");
           return ResponseEntity.badRequest().body(new MessageResponse("You do not have access to this resource"));
       }

       Set<User> nonAdminUsers = userRepository.findUsersByRolesName(ERole.USER);

       LOGGER.info("Fetched all users");

        return ResponseEntity.ok( nonAdminUsers.stream().map(user -> {
          
            return DtoMapper.toDto(user, UserDto3.class);
        })    .collect(Collectors.toList()));
   }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User does not exist");
        }

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.INACTIVE)) {
            LOGGER.error("Status Update Failed: User status is already INACTIVE");
            throw new RuntimeException("User is already deleted");
        }

        userExist.setStatus(Status.INACTIVE);
        userRepository.save(userExist);

        LOGGER.info("User status successfully changed to INACTIVE");
    }

    @Override
    public void restrictUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User does not exist");
        }

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.RESTRICTED)) {
            LOGGER.error("Status Update Failed: User status is already RESTRICTED");
            throw new RuntimeException("User is already restricted");
        }
        userExist.setStatus(Status.RESTRICTED);
        userRepository.save(userExist);

        LOGGER.info("User status successfully changed to RESTRICTED");
    }

    @Override
    public void activateUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User does not exist");
        }

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.ACTIVE)) {
            LOGGER.error("Status Update Failed: User status is already ACTIVE");
            throw new RuntimeException("User is already active");
        }

        userExist.setStatus(Status.ACTIVE);
        userRepository.save(userExist);

        LOGGER.info("User status successfully changed to ACTIVE");
    }

}
