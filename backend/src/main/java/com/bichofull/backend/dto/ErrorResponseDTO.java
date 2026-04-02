package com.bichofull.backend.dto;

import java.time.LocalDateTime;

// Utilizamos record para um DTO simples e imutável
public record ErrorResponseDTO(
    String message,      // Mensagem amigável do erro
    int status,         // Código HTTP (ex: 400, 404, 500)
    LocalDateTime timestamp
) {}