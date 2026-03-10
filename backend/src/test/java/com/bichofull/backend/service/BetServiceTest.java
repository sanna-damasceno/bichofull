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

    // 1️⃣ Criar aposta com sucesso
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

    // 2️⃣ Não permitir aposta com saldo insuficiente
    @Test
    void shouldNotAllowBetWithInsufficientBalance() {

        User user = new User();
        user.setEmail("saldo@email.com");
        user.setName("Saldo");
        user.setPasswordHash("123");
        user.setBalance(BigDecimal.valueOf(5));

        userRepository.save(user);

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.GROUP);
        request.setChosenNumber("12");
        request.setAmount(BigDecimal.valueOf(100));

        assertThrows(RuntimeException.class, () -> {
            betService.createBetDTO(request, "saldo@email.com");
        });
    }

    // 3️⃣ Histórico de apostas do usuário
    @Test
    void shouldReturnUserBetHistory() {

        User user = new User();
        user.setEmail("history@email.com");
        user.setName("History");
        user.setPasswordHash("123");
        user.setBalance(BigDecimal.valueOf(100));

        userRepository.save(user);

        BetRequestDTO request = new BetRequestDTO();
        request.setType(BetType.THOUSAND);
        request.setChosenNumber("1234");
        request.setAmount(BigDecimal.valueOf(10));

        betService.createBetDTO(request, "history@email.com");

        var bets = betService.getUserBetsDTO("history@email.com");

        assertFalse(bets.isEmpty());
        assertEquals("1234", bets.get(0).getChosenNumber());
    }
}