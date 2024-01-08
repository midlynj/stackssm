package com.example.stacks.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminInterface {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

   @Override
    public ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail) {
        Optional<User> userOptional = userRepository.findUserByEmail(adminEmail.getEmail());

       if (userOptional.isEmpty())
            throw new RuntimeException("Email not found");

       User userExist = userOptional.get();

       Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));

       if (!userExist.getRoles().contains(adminRole))
            return ResponseEntity.badRequest().body(new MessageResponse("You do not have access to this resource"));

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

        return ResponseEntity.ok(userDto3List);
   }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new RuntimeException("User does not exist");

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.INACTIVE))
            throw new RuntimeException("User is already deleted");

        userExist.setStatus(Status.INACTIVE);
        userRepository.save(userExist);
    }

    @Override
    public void restrictUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new RuntimeException("User does not exist");

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.RESTRICTED))
            throw new RuntimeException("User is already restricted");

        userExist.setStatus(Status.RESTRICTED);
        userRepository.save(userExist);
    }

    @Override
    public void activateUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new RuntimeException("User does not exist");

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.ACTIVE))
            throw new RuntimeException("User is already active");

        userExist.setStatus(Status.ACTIVE);
        userRepository.save(userExist);
    }

}
