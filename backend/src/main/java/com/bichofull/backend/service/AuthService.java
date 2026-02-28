package com.bichofull.backend.service;

import com.bichofull.backend.dto.RegisterRequestDTO;
import com.bichofull.backend.dto.RegisterResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(encryptedPassword);

        user.setBalance(new BigDecimal("1000.00"));

        User savedUser = userRepository.save(user);

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getBalance()
        );
    }
    
}