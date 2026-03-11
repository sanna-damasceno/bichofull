package com.bichofull.backend.controller;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.service.BetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
@Tag(name = "Apostas", description = "Gerenciamento de apostas")
@RestController
@RequestMapping("/api/bets")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @Operation(
        summary = "Criar aposta",
        description = "Permite ao usuário registrar uma aposta caso possua saldo suficiente."
    )
    @PostMapping
    public ResponseEntity<BetResponseDTO> createBet(
            @RequestBody BetRequestDTO request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        BetResponseDTO bet = betService.createBetDTO(request, email);

        return ResponseEntity.ok(bet);
    }

    @Operation(
        summary = "Histórico de apostas",
        description = "Retorna todas as apostas realizadas pelo usuário autenticado."
    )

    @GetMapping("/my-bets")
    public ResponseEntity<List<BetResponseDTO>> getMyBets(Authentication authentication) {

        String email = authentication.getName();

        List<BetResponseDTO> bets = betService.getUserBetsDTO(email);

        return ResponseEntity.ok(bets);
    }
}