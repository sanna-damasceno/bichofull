package com.bichofull.backend.controller;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.service.BetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bets")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping
    public ResponseEntity<BetResponseDTO> createBet(
            @RequestBody BetRequestDTO request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        BetResponseDTO bet = betService.createBetDTO(request, email);

        return ResponseEntity.ok(bet);
    }

    @GetMapping("/my-bets")
    public ResponseEntity<List<BetResponseDTO>> getMyBets(Authentication authentication) {

        String email = authentication.getName();

        List<BetResponseDTO> bets = betService.getUserBetsDTO(email);

        return ResponseEntity.ok(bets);
    }
}