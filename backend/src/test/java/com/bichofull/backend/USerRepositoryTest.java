package com.bichofull.backend;
import java.math.BigDecimal;

import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPasswordHash("123");
        user.setBalance(new BigDecimal("100.00"));


        userRepository.save(user);
    }
}