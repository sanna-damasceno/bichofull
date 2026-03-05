package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.enums.BetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BetServiceTest {

    @Autowired
    private BetService betService;

    // Criar aposta com sucesso
    @Test
    void shouldCreateBetSuccessfully() {

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.GROUP);
        request.setChosenNumber("12");
        request.setAmount(BigDecimal.valueOf(10));

        BetResponseDTO bet = betService.createBetDTO(request, "larissa@email.com");

        assertNotNull(bet);
        assertEquals("12", bet.getChosenNumber());
        assertEquals(BigDecimal.valueOf(10), bet.getAmount());
    }
    //Teste de erro (saldo insuficiente)
    @Test
    void shouldNotAllowBetWithInsufficientBalance() {

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.GROUP);
        request.setChosenNumber("12");
        request.setAmount(BigDecimal.valueOf(100000));

        assertThrows(RuntimeException.class, () -> {
            betService.createBetDTO(request, "larissa@email.com");
        });
    }
}