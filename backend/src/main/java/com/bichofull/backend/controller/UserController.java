package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.dto.UserBalanceResponseDTO;
import com.bichofull.backend.service.UserService;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
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
    
    @GetMapping("/balance")
    public UserBalanceResponseDTO getBalance(Authentication authentication) {

        String email = authentication.getName();

        return userService.getBalance(email);
    }
}