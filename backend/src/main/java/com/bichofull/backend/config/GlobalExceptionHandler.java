package com.bichofull.backend.config;

import com.bichofull.backend.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice // Esta anotação diz ao Spring para monitorar todos os Controllers
public class GlobalExceptionHandler {

    // 1. Captura erros de validação ou regras de negócio (ex: Saldo insuficiente)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Captura erros quando algo não é encontrado (ex: Usuário não existe)
    // Você pode usar isso para o erro no UserController.java
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntime(RuntimeException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "Erro interno: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 3. Captura erros de segurança (Acesso negado)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "Você não tem permissão para realizar esta ação.",
            HttpStatus.FORBIDDEN.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}