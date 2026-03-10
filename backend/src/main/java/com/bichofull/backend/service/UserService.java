package com.bichofull.backend.service;

import com.bichofull.backend.dto.UserBalanceResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserBalanceResponseDTO getBalance(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserBalanceResponseDTO(user.getBalance());
    }
}