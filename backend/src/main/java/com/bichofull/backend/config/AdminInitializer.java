package com.bichofull.backend.config;

import com.bichofull.backend.enums.UserRole;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {

            String adminEmail = "admin@bichofull.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setBalance(BigDecimal.ZERO);
                admin.setRole(UserRole.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());

                userRepository.save(admin);

                System.out.println("ADMIN USER CREATED");
            }
        };
    }
}