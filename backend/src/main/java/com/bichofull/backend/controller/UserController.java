package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public UserResponseDTO getAuthenticatedUser(Authentication authentication) {

        String email = authentication.getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance()
        );
    }
}