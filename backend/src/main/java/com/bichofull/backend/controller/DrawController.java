package com.bichofull.backend.controller;

import com.bichofull.backend.dto.DrawRequestDTO;
import com.bichofull.backend.dto.DrawResponseDTO;
import com.bichofull.backend.model.Draw;
import com.bichofull.backend.service.DrawService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/draws")
public class DrawController {

    private final DrawService drawService;

    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @PostMapping
    public ResponseEntity<DrawResponseDTO> createDraw(@RequestBody DrawRequestDTO request) {

        Draw saved = drawService.createDraw(request);

        return ResponseEntity.ok(drawService.toDTO(saved));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/run")
    public ResponseEntity<DrawResponseDTO> runDraw() {

        Draw draw = drawService.runDraw();

        return ResponseEntity.ok(drawService.toDTO(draw));
    }
}