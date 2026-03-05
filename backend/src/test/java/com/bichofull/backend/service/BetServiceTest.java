package com.bichofull.backend.service;

import com.bichofull.backend.dto.BetRequestDTO;
import com.bichofull.backend.dto.BetResponseDTO;
import com.bichofull.backend.enums.BetType;
import com.bichofull.backend.model.User;
import com.bichofull.backend.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BetServiceTest {

    @Autowired
    private BetService betService;

    @Autowired
    private UserRepository userRepository;

    // Criar aposta com sucesso
    @Test
    void shouldCreateBetSuccessfully() {

        User user = new User();
        user.setEmail("teste@email.com");
        user.setName("Teste");
        user.setPasswordHash("123");
        user.setBalance(BigDecimal.valueOf(100));

        userRepository.save(user);

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.GROUP);
        request.setChosenNumber("12");
        request.setAmount(BigDecimal.valueOf(10));

        BetResponseDTO bet = betService.createBetDTO(request, "teste@email.com");

        assertNotNull(bet);
        assertEquals("12", bet.getChosenNumber());
        assertEquals(BigDecimal.valueOf(10), bet.getAmount());
    }

    // Teste de erro (saldo insuficiente)
    @Test
    void shouldNotAllowBetWithInsufficientBalance() {

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.GROUP);
        request.setChosenNumber("12");
        request.setAmount(BigDecimal.valueOf(100000));

        assertThrows(RuntimeException.class, () -> {
            betService.createBetDTO(request, "teste@email.com");
        });
    }
}