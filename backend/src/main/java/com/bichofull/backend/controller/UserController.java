package com.bichofull.backend.controller;

import com.bichofull.backend.dto.UserResponseDTO;
import com.bichofull.backend.model.User;
import com.bichofull.backend.dto.UserBalanceResponseDTO;
import com.bichofull.backend.service.UserService;
import com.bichofull.backend.service.BetService;
import com.bichofull.backend.repository.UserRepository;

import java.math.BigDecimal;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuário", description = "Informações do usuário autenticado")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BetService betService;

    public UserController(UserRepository userRepository, UserService userService, BetService betService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.betService = betService;
    }

    @Operation(
        summary = "Dados do usuário",
        description = "Retorna informações do usuário autenticado."
    )

    @GetMapping("/me")
    public UserResponseDTO getAuthenticatedUser(Authentication authentication) {

        String email = authentication.getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal won = betService.sumAmountByUserAndStatus(user.getId(), com.bichofull.backend.enums.BetStatus.WON);
        BigDecimal lost = betService.sumAmountByUserAndStatus(user.getId(), com.bichofull.backend.enums.BetStatus.LOST);
        BigDecimal pending = betService.calculateTotalPendingPrize(user.getId());
       
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBalance(),
                won, lost, pending
        );
    }

    @Operation(
        summary = "Saldo do usuário",
        description = "Retorna o saldo atual do usuário."
    )

    @GetMapping("/balance")
    public UserBalanceResponseDTO getBalance(Authentication authentication) {

        String email = authentication.getName();

        return userService.getBalance(email);
    }
}