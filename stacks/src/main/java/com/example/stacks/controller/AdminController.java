package com.example.stacks.controller;

import com.example.stacks.dto.UserDto3;
import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.AdminAccess;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.repository.RoleRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/admin", headers = "Accept=application/json")
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/all/users")
    public ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail) {
        Optional<User> userOptional = userRepository.findUserByEmail(adminEmail.getEmail());

        Set<User> nonAdminUsers = userRepository.findUsersByRolesName(ERole.USER);

        List<UserDto3> userDto3List = new ArrayList<>();

        for (User user: nonAdminUsers) {
            UserDto3 userDto3 = new UserDto3();
            userDto3.setId(user.getId());
            userDto3.setFirstName(user.getFirstName());
            userDto3.setLastName(user.getLastName());
            userDto3.setEmail(user.getEmail());
            userDto3.setStatus(user.getStatus());
            userDto3List.add(userDto3);
        }

        if (userOptional.isEmpty())
            throw new RuntimeException("Email not found");


//        if (userOptional.isEmpty())
//            return ResponseEntity.badRequest().body(new MessageResponse("No user found with that email"));

        Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));

        User userExist = userOptional.get();

        if (!userExist.getRoles().contains(adminRole))
            return ResponseEntity.badRequest().body(new MessageResponse("You don't have access to this resource"));
//return ResponseEntity.ok(nonAdminUsers);//return all information still debating which i want to use
        return ResponseEntity.ok(userDto3List); //this only return required information I need


//        return userService.fetchAllUsers();
//        return ResponseEntity.ok(friendService.fetchEntitiesWithMapper(userRepository, User.class, UserDto3.class));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.INACTIVE))
            return ResponseEntity.badRequest().body(new MessageResponse("User is already deleted"));

        userExist.setStatus(Status.INACTIVE);
        userRepository.save(userExist);

        return ResponseEntity.ok(new MessageResponse("User successfully deleted"));
    }

    @PutMapping("/restrict/{id}")
    public ResponseEntity<?> restrictUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.RESTRICTED))
            return ResponseEntity.badRequest().body(new MessageResponse("User is already restricted"));

        userExist.setStatus(Status.RESTRICTED);
        userRepository.save(userExist);

        return ResponseEntity.ok(new MessageResponse("User successfully deleted"));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.ACTIVE))
            return ResponseEntity.badRequest().body(new MessageResponse("User is already active"));

        userExist.setStatus(Status.ACTIVE);
        userRepository.save(userExist);

        return ResponseEntity.ok(new MessageResponse("User successfully activated"));
    }

}
