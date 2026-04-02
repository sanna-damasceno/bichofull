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
        // O CommandLineRunner executa este bloco assim que a aplicação termina de subir
        return args -> {
            String adminEmail = "admin@bichofull.com";

            // Verifica se o banco de dados já possui o usuário admin para evitar duplicatas
            if (userRepository.findByEmail(adminEmail).isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                // A senha "admin123" é criptografada antes de ser salva por segurança
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setBalance(BigDecimal.ZERO);
                admin.setRole(UserRole.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());

                userRepository.save(admin); // Salva o administrador padrão no banco
                System.out.println("ADMIN USER CREATED");
            }
        };
    }
}