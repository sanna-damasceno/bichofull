package com.bichofull.backend.service;

import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnUserBalance() {

        String email = "user@test.com";

        User user = new User();
        user.setEmail(email);
        user.setBalance(new BigDecimal("150"));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var result = userService.getBalance(email);

        assertEquals(new BigDecimal("150"), result.getBalance());
    }
}