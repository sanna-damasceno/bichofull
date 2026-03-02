package com.bichofull.backend.controller;

import com.bichofull.backend.dto.LoginRequestDTO;
import com.bichofull.backend.dto.LoginResponseDTO;
import com.bichofull.backend.dto.RegisterRequestDTO;
import com.bichofull.backend.dto.RegisterResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {

        try {
            RegisterResponseDTO response = authService.register(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        try {
            LoginResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}