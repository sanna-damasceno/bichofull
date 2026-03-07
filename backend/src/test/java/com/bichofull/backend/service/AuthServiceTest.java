package com.bichofull.backend.service;

import com.bichofull.backend.config.JwtService;
import com.bichofull.backend.dto.LoginRequestDTO;
import com.bichofull.backend.dto.RegisterRequestDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Cadastro com sucesso

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Sanna");
        request.setEmail("sanna@email.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encryptedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        var response = authService.register(request);

        assertEquals("Sanna", response.getName());
        assertEquals("sanna@email.com", response.getEmail());
        assertEquals(new BigDecimal("1000.00"), response.getBalance());

        verify(userRepository).save(any(User.class));
    }

    // Cadastro com email duplicado
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("Sanna");
        request.setEmail("sanna@email.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                authService.register(request)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    // Login com sucesso
    @Test
    void shouldLoginSuccessfully() {

        var user = new User();
        user.setEmail("sanna@email.com");
        user.setPasswordHash("encryptedPassword");

        when(userRepository.findByEmail("sanna@email.com"))
                .thenReturn(java.util.Optional.of(user));

        when(passwordEncoder.matches("123456", "encryptedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken(any(User.class)))
                .thenReturn("fake-jwt-token");

        LoginRequestDTO request =
                new LoginRequestDTO("sanna@email.com", "123456");

        var response = authService.login(request);

        assertEquals("fake-jwt-token", response.getToken());
        
        verify(jwtService).generateToken(any(User.class));
    }

    // Login com senha errada

    @Test
    void shouldThrowExceptionWhenPasswordIsWrong() {

        var user = new User();
        user.setEmail("sanna@email.com");
        user.setPasswordHash("encryptedPassword");

        when(userRepository.findByEmail("sanna@email.com"))
                .thenReturn(java.util.Optional.of(user));

        when(passwordEncoder.matches("wrong", "encryptedPassword"))
                .thenReturn(false);

        LoginRequestDTO request =
                new LoginRequestDTO("sanna@email.com", "wrong");

        assertThrows(IllegalArgumentException.class, () ->
                authService.login(request)
        );
    }



}