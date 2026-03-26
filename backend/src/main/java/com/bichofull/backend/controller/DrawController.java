package com.bichofull.backend.controller;

import com.bichofull.backend.dto.DrawRequestDTO;
import com.bichofull.backend.dto.DrawResponseDTO;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.service.DrawService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sorteios", description = "Execução e registro de sorteios")
@RestController
@RequestMapping("/api/draws")
public class DrawController {

    private final DrawService drawService;

    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @Operation(
        summary = "Criar sorteio manual",
        description = "Cria um sorteio com números definidos."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DrawResponseDTO> createDraw(@RequestBody DrawRequestDTO request) {

        Draw saved = drawService.createDraw(request);

        return ResponseEntity.ok(drawService.toDTO(saved));
    }

    @Operation(
        summary = "Executar sorteio",
        description = "Gera um sorteio aleatório e processa todas as apostas pendentes."
    )

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/run")
    public ResponseEntity<DrawResponseDTO> runDraw() {

        Draw draw = drawService.runDraw();

        return ResponseEntity.ok(drawService.toDTO(draw));
    }

    @GetMapping("/last")
    public ResponseEntity<DrawResponseDTO> getLastDraw() {

        Draw draw = drawService.getLastDraw();

        if (draw == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(drawService.toDTO(draw));
    }


    @GetMapping("/history")
    public ResponseEntity<List<DrawResponseDTO>> getHistory() {

        List<Draw> draws = drawService.getDrawHistory();

        List<DrawResponseDTO> response = draws.stream()
                .map(drawService::toDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
}